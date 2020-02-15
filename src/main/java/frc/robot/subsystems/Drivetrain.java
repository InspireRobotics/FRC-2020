package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.command.JoystickDriveCommand;

/**
 * The drivetrain on the robot.
 * 
 * This is responsible for managing the state of the drivetrain, including
 * encoder values (velocity, position, etc.)
 */
public class Drivetrain extends SubsystemBase {

    private CANSparkMax fl;
    private CANSparkMax fr;

    private DifferentialDrive drive;

    /**
     * Initializes the tank drivetrain on the robot
     * 
     * @param robot
     *            The robot instance
     */
    @Override
    public void init(Robot robot) {

        // Get the motors
        fl = new CANSparkMax(Constants.CAN.DRIVE_FL, MotorType.kBrushless);
        fr = new CANSparkMax(Constants.CAN.DRIVE_FR, MotorType.kBrushless);
        CANSparkMax bl = new CANSparkMax(Constants.CAN.DRIVE_BL, MotorType.kBrushless);
        CANSparkMax br = new CANSparkMax(Constants.CAN.DRIVE_BR, MotorType.kBrushless);

        // Reset the encoders
        fl.getEncoder().setPosition(0.0);
        fr.getEncoder().setPosition(0.0);

        // Invert the motors as needed
        fl.setInverted(true);
        bl.setInverted(true);

        fr.setInverted(true);
        br.setInverted(true);

        // Group the motors and assign them to the drivetrain
        SpeedControllerGroup left = new SpeedControllerGroup(fl, bl);
        SpeedControllerGroup right = new SpeedControllerGroup(fr, br);

        drive = new DifferentialDrive(left, right);

        // Bind joystick control to the drivetrain
        setDefaultCommand(new JoystickDriveCommand());
    }

    /**
     * Resets power output as per safety regulations.
     */
    @Override
    public void periodic() {
        // Reset the power to zero every loop
        // to prevent safety warning
        setPower(0, 0);
    }

    /**
     * Disables the motors upon disabling the drivetrain
     */
    @Override
    public void disable() {
        // Set the power to zero, just in case periodic didn't set it.
        setPower(0, 0);
    }

    /**
     * Sets the power output to the drivetrain as a percentage
     * 
     * @param left
     *            PWM pulse width for left two motors (0.0-1.0)
     * @param right
     *            PWM pulse width for right two motors (0.0-1.0)
     */
    public void setPower(double left, double right) {
        drive.tankDrive(left, right);
    }

    /**
     * Gets the name of the subsystem of the robot
     * 
     * @return The name of the system.
     */
    @Override
    String getSubsystemName() {
        return "Drivetrain";
    }

    /**
     * Gets the drive object of the drivetrain; Should not be used for actual
     * driving.
     * 
     * @return The DifferentialDrive bound to the drivetrain
     */
    public DifferentialDrive getDrive() {
        return drive;
    }

    /**
     * Gets the encoder position of the front left motor.
     * 
     * @return The current position of the front left encoder
     */
    public double leftEncoder() {
        return -fl.getEncoder().getPosition();
    }

    /**
     * Gets the encoder position of the front right motor.
     * 
     * @return The current position of the front right encoder
     */
    public double rightEncoder() {
        return fr.getEncoder().getPosition();
    }
}