package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.drive.Vector2d;

import frc.robot.Constants;
import frc.robot.Robot;

import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.links.I2CLink;

/**
 * The collection and hopper subsystem.
 *
 * Responsible for collecting balls, managing them in storage, and feeding them
 * to the Shooter subsystem.
 */
public class Hopper extends SubsystemBase {

    private final long EJECT_TIME_MS = 200;
    private final long BLOCK_REFRESH = 200;
    private final double PERCENT_OUTPUT = 1;

    private final VictorSPX hopperHold;
    private final VictorSPX hopperLift;
    private final CANSparkMax hopperSpinBottom;
    private final CANSparkMax hopperSpinTop;

    private final DoubleSolenoid intake;

    private final DigitalInput intakeGate;
    private final Pixy2 pixycam;

    private byte m_ballCount;
    private long removeStart;
    private long blockTimeout;

    /**
     * Default constructor
     */
    public Hopper() {
        hopperHold = new VictorSPX(Constants.CAN.HOPPER_HOLD);
        hopperLift = new VictorSPX(Constants.CAN.HOPPER_LIFT);
        hopperSpinBottom = new CANSparkMax(Constants.CAN.HOPPER_SPIN_BOTTOM, MotorType.kBrushless);
        hopperSpinTop = new CANSparkMax(Constants.CAN.HOPPER_SPIN_TOP, MotorType.kBrushless);

        intake = new DoubleSolenoid(1, Constants.PNEUMATICS.FLIP_INTAKE_01,
                Constants.PNEUMATICS.FLIP_INTAKE_02);

        intakeGate = new DigitalInput(Constants.DIO.PHOTOGATE);
        pixycam = Pixy2.createInstance(new I2CLink());
        pixycam.init();
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
        hopperHold.set(ControlMode.PercentOutput, 0);
        hopperLift.set(ControlMode.PercentOutput, 0);
        hopperSpinBottom.set(0);
        hopperSpinTop.set(0);
    }

    /**
     * Resets the motors as per the safety regulations.
     */
    @Override
    public void periodic() {
        hopperHold.set(ControlMode.PercentOutput, 0);
        hopperLift.set(ControlMode.PercentOutput, 0);
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
            hopperSpinBottom.set(-PERCENT_OUTPUT);
            hopperSpinTop.set(PERCENT_OUTPUT * 0.1);
        case 2:
        case 3:
            hopperLift.set(ControlMode.PercentOutput, PERCENT_OUTPUT);
        case 4:
            hopperHold.set(ControlMode.PercentOutput, -PERCENT_OUTPUT);
            break;
        case 5:
            hopperHold.set(ControlMode.PercentOutput, 0);
            break;
        default:
            hopperHold.set(ControlMode.PercentOutput, -PERCENT_OUTPUT);
            hopperLift.set(ControlMode.PercentOutput, 0);
            hopperSpinBottom.set(0);
            hopperSpinTop.set(0);
            break;
        }

        if (System.currentTimeMillis() - removeStart < EJECT_TIME_MS) {
            hopperSpinTop.set(-PERCENT_OUTPUT);
            hopperSpinBottom.set(-PERCENT_OUTPUT);
            hopperHold.set(ControlMode.PercentOutput, -PERCENT_OUTPUT);
            hopperLift.set(ControlMode.PercentOutput, PERCENT_OUTPUT);
        }

        if (intakeGate.get()) {
            if (blockTimeout + BLOCK_REFRESH < System.currentTimeMillis()) {
                m_ballCount++;
                System.out.println("Current Ballcount: " + m_ballCount);
            }
            blockTimeout = System.currentTimeMillis();
        }
    }

    public void lowerIntake() {
        intake.set(DoubleSolenoid.Value.kForward);
    }

    public void raiseIntake() {
        intake.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggleIntake() {
        if (intake.get() == DoubleSolenoid.Value.kForward) {
            intake.set(DoubleSolenoid.Value.kReverse);
        } else {
            intake.set(DoubleSolenoid.Value.kForward);
        }
    }

    /**
     * Eject a ball from the subsystem, and deduct one ball from the counter.
     */
    public void removeBall() {
        removeStart = ((removeStart < System.currentTimeMillis()) ? System.currentTimeMillis()
                : removeStart) + EJECT_TIME_MS;
        m_ballCount--;
    }

    public byte ballCount() {
        return m_ballCount;
    }

    public void resetBallCount() {
        m_ballCount = 0;
    }

    /**
     * Gets the position of the largest ball relative to the PixyCam
     *
     * @return Returns a vector representing the position of the ball in the
     *         coordinates of the PixyCam's view.
     */
    public Vector2d ballPosition() {
        ArrayList<Pixy2CCC.Block> blocks = pixycam.getCCC().getBlocks();
        if (blocks.size() > 0) {
            Pixy2CCC.Block largest = null;
            for (Pixy2CCC.Block temp : blocks) {
                // Check aspect ratio
                if ((float) temp.getWidth() / temp.getHeight() > 1.2
                        || (float) temp.getHeight() / temp.getWidth() < 0.8) {
                    continue;
                }
                if (largest == null || (temp.getWidth() * temp.getHeight() > largest.getWidth()
                        * largest.getHeight())) {
                    largest = temp;
                }
            }
            if (largest != null) {
                return new Vector2d(largest.getX(), largest.getY());
            }
        }

        return null;
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
