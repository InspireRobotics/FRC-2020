package frc.robot.subsystems;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.Vector2d;

import frc.robot.Constants;
import frc.robot.Robot;

/**
 * The collection and hopper subsystem.
 *
 * Responsible for collecting balls, managing them in storage, and feeding them
 * to the Shooter subsystem.
 */
public class Hopper extends SubsystemBase {

    private final long EJECT_TIME_MS = 200;
    private final long BLOCK_REFRESH = 200;

    private final VictorSPX intake;
    private final VictorSPX hopperHold;
    private final Spark hopperLift;
    private final Spark hopperSpinBottom;
    private final Spark hopperSpinTop;
    private final DigitalOutput intakeGate;
    private final I2C pixycam;

    private byte m_ballCount;
    private long removeStart;
    private long blockTimeout;

    /**
     * Default constructor
     */
    public Hopper() {
        intake = new VictorSPX(Constants.CAN.INTAKE);
        hopperHold = new VictorSPX(Constants.CAN.HOPPER_HOLD);
        hopperLift = new Spark(Constants.CAN.HOPPER_LIFT);
        hopperSpinBottom = new Spark(Constants.CAN.HOPPER_SPIN_BOTTOM);
        hopperSpinTop = new Spark(Constants.CAN.HOPPER_SPIN_TOP);

        intakeGate = new DigitalOutput(Constants.DIO.PHOTOGATE);
        pixycam = new I2C(I2C.Port.kMXP, Constants.I2C.PIXYCAM);
    }

    /**
     * Initializes the subsystem for use with the robot.
     * 
     * @param robot
     *            The robot instance.
     */
    @Override
    public void init(Robot robot) {
        m_ballCount = 0;
        removeStart = System.currentTimeMillis() - EJECT_TIME_MS;

    }

    /**
     * Disables the hopper. Stops all motors in the subsystems.
     */
    @Override
    public void disable() {
        intake.set(ControlMode.PercentOutput, 0);
        hopperHold.set(ControlMode.PercentOutput, 0);
        hopperLift.set(0);
        hopperSpinBottom.set(0);
        hopperSpinTop.set(0);
    }

    /**
     * Resets the motors as per the safety regulations.
     */
    @Override
    public void periodic() {
        intake.set(ControlMode.PercentOutput, 0);
        hopperHold.set(ControlMode.PercentOutput, 0);
        hopperLift.set(0);
        hopperSpinBottom.set(0);
        hopperSpinTop.set(0);
    }

    /**
     * Sets motor values for the hopper as appropriate. Must be called separately
     * from and after periodic().
     */
    public void runHopper() {
        switch (m_ballCount) {
        case 1:
        case 2:
            hopperSpinBottom.set(1);
            hopperSpinTop.set(1);
        case 3:
            hopperLift.set(1);
        case 4:
        case 5:
            hopperHold.set(ControlMode.PercentOutput, 1);
            break;
        default:
            hopperHold.set(ControlMode.PercentOutput, 0);
            hopperLift.set(0);
            hopperSpinBottom.set(0);
            hopperSpinTop.set(0);
            break;
        }

        if (System.currentTimeMillis() - removeStart < EJECT_TIME_MS) {
            hopperSpinTop.set(1);
            hopperSpinBottom.set(1);
        }

        if (intakeGate.get()) {
            if (blockTimeout + BLOCK_REFRESH < System.currentTimeMillis()) {
                m_ballCount++;
            }
            blockTimeout = System.currentTimeMillis();
        }
    }

    /**
     * Eject a ball from the subsystem, and deduct one ball from the counter.
     */
    public void removeBall() {
        removeStart = (removeStart < System.currentTimeMillis()) ? System.currentTimeMillis()
                : (removeStart + EJECT_TIME_MS);
        m_ballCount--;
    }

    public void runIntake() {
        intake.set(ControlMode.PercentOutput, 1);
    }

    public byte ballCount() {
        return m_ballCount;
    }

    /**
     * Queries the pixycam with a packet, and receives a packet in return.
     * 
     * @param request
     *            Bytes to send to the Pixy. NOTE THAT THIS METHOD ASSUMES CORRECT
     *            FORMATTING!
     * @return Returns the response data with checksum applied. NOTE THAT THESE ARE
     *         THE RAW BYTES, YOU ARE RESPONSIBLE FOR PROPER INTERPRETATION!
     */
    public byte[] queryCam(byte[] request) throws IOException {
        pixycam.writeBulk(request);

        ByteBuffer header = ByteBuffer.allocate(2);
        pixycam.readOnly(header, 2);
        header.order(ByteOrder.LITTLE_ENDIAN);

        if (header.getInt() == Constants.PIXYCAM.CHECKSUM_SYNC) {
            ByteBuffer padding = ByteBuffer.allocate(2);
            pixycam.readOnly(padding, 2);

            ByteBuffer checkSum = ByteBuffer.allocate(2);
            pixycam.readOnly(checkSum, 2);
            checkSum.order(ByteOrder.LITTLE_ENDIAN);

            if (padding.get(1) != 0) {
                ByteBuffer output = ByteBuffer.allocate(padding.get(1));
                pixycam.readOnly(output, 0);

                int sum = 0;
                for (byte b : output.array()) {
                    sum += b;
                }

                if (checkSum.getInt() != sum) {
                    throw new IOException("Checksum did not equate to received data.");
                }
                return output.array();
            } else {
                return null;
            }
        } else {
            throw new IOException("Pixycam responded with bad header");
        }
    }

    /**
     * Gets the position of the ball relative to the PixyCam
     *
     * FIXME: Is currently untested, and may fail.
     *
     * @return Returns a vector representing the position of the ball in the
     *         coordinates of the PixyCam's view.
     * @throws IOException
     *             The PixyCam may fail to respond, and the exception should be
     *             caught by the caller.
     *
     *             TODO: Update with code filter for blocks, multiple blocks are
     *             counted as seen with this GitHub post:
     *             https://github.com/olentangyfrc/DeepSpace-2019/issues/234#issuecomment-460079274
     */
    public Vector2d ballPosition() throws IOException {
        byte[] result = queryCam(
                new byte[] { (byte) 174, (byte) 193, (byte) 32, (byte) 2, (byte) 255, (byte) 1 });
        return new Vector2d(ByteBuffer.wrap(result, 2, 2).order(ByteOrder.LITTLE_ENDIAN).getInt(),
                ByteBuffer.wrap(result, 4, 2).order(ByteOrder.LITTLE_ENDIAN).getInt());
    }

    /**
     * Gets the name of the subsystem as a String.
     * 
     * @return Returns the name of the subsystem as a String. Always "Hopper".
     */
    @Override
    public String getSubsystemName() {
        return "Hopper";
    }
}
