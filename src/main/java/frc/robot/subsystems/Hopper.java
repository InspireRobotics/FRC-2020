package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;

import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.robot.Constants;
import frc.robot.Robot;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Hopper extends SubsystemBase {

    private final long EJECT_TIME_MS = 200;
    private final long BLOCK_REFRESH = 200;

    public final Spark intake;
    public final Spark hopperHold;
    public final Spark hopperLift;
    public final Spark hopperSpinBottom;
    public final Spark hopperSpinTop;
    public final DigitalOutput intakeGate;
    public final I2C pixycam;

    private char m_ballCount;
    private long removeStart;
    private long blockTimeout;

    public Hopper() {
        intake = new Spark(Constants.CAN.INTAKE);
        hopperHold = new Spark(Constants.CAN.HOPPER_HOLD);
        hopperLift = new Spark(Constants.CAN.HOPPER_LIFT);
        hopperSpinBottom = new Spark(Constants.CAN.HOPPER_SPIN_BOTTOM);
        hopperSpinTop = new Spark(Constants.CAN.HOPPER_SPIN_TOP);
        intakeGate = new DigitalOutput(Constants.DIO.PHOTOGATE);
        pixycam = new I2C(I2C.Port.kMXP, Constants.I2C.PIXYCAM);
    }

    @Override
    public void init(Robot robot) {
        m_ballCount = 0;
        removeStart = System.currentTimeMillis() - EJECT_TIME_MS;

    }

    @Override
    public void disable() {
        intake.set(0);
        hopperHold.set(0);
        hopperLift.set(0);
        hopperSpinBottom.set(0);
        hopperSpinTop.set(0);
    }

    @Override
    public void periodic() {
        intake.set(0);
        hopperHold.set(0);
        hopperLift.set(0);
        hopperSpinBottom.set(0);
        hopperSpinTop.set(0);
    }

    public void runHopper() {
        intake.set(1);
        switch (m_ballCount) {
        case 1:
        case 2:
            hopperSpinBottom.set(1);
            hopperSpinTop.set(1);
        case 3:
            hopperLift.set(1);
        case 4:
        case 5:
            hopperHold.set(1);
            break;
        default:
            hopperHold.set(0);
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

    public void removeBall() {
        removeStart = (removeStart < System.currentTimeMillis()) ? System.currentTimeMillis()
                : (removeStart + EJECT_TIME_MS);
        m_ballCount--;
    }


    /** Queries the pixycam with a packet, and receives a packet in return.
     * @param request Bytes to send to the Pixy. NOTE THAT THIS METHOD ASSUMES CORRECT FORMATTING!
     * @return Returns the response data with checksum applied. NOTE THAT THESE ARE THE RAW BYTES, YOU ARE RESPONSIBLE FOR PROPER INTERPRETATION!
     */
    public byte[] queryCam(byte[] request) throws IOException {
        pixycam.writeBulk(request);

        ByteBuffer header = ByteBuffer.allocate(2);
        pixycam.readOnly(header, 2);
        header = header.order(ByteOrder.LITTLE_ENDIAN);

        if (header.getInt() == Constants.PIXYCAM.CHECKSUM_SYNC) {
            ByteBuffer padding = ByteBuffer.allocate(2);
            pixycam.readOnly(padding, 2);

            ByteBuffer checkSum = ByteBuffer.allocate(2);
            pixycam.readOnly(checkSum, 2);
            checkSum = checkSum.order(ByteOrder.LITTLE_ENDIAN);

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

    //TODO: Make this less jank
    public Vector2d ballPosition() throws IOException {
        ByteBuffer request = ByteBuffer.allocate(6);
        request.put(new byte[]{(byte) 174, (byte) 193, (byte) 32, (byte) 2, (byte) 255, (byte) 1});
        byte[] result = queryCam(request.array());
        return new Vector2d(ByteBuffer.wrap(result, 2, 2).order(ByteOrder.LITTLE_ENDIAN).getInt(),
                ByteBuffer.wrap(result, 4, 2).order(ByteOrder.LITTLE_ENDIAN).getInt());
    }

    @Override
    public String getSubsystemName() {
        return "Hopper";
    }
}
