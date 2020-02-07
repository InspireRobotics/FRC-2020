package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
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
    private CANSparkMax bl;
    private CANSparkMax br;

    private PIDController leftControl;
    private PIDController rightControl;

    private DifferentialDrive drive;

    @Override
    public void init(Robot robot) {
        fl = new CANSparkMax(Constants.CAN.DRIVE_FL, MotorType.kBrushless);
        fr = new CANSparkMax(Constants.CAN.DRIVE_FR, MotorType.kBrushless);
        bl = new CANSparkMax(Constants.CAN.DRIVE_BL, MotorType.kBrushless);
        br = new CANSparkMax(Constants.CAN.DRIVE_BR, MotorType.kBrushless);

        /*
         * fl.getEncoder().setPositionConversionFactor(Constants.ENCODER.
         * COUNTS_TO_INCHES);
         * fr.getEncoder().setPositionConversionFactor(Constants.ENCODER.
         * COUNTS_TO_INCHES);
         * bl.getEncoder().setPositionConversionFactor(Constants.ENCODER.
         * COUNTS_TO_INCHES);
         * br.getEncoder().setPositionConversionFactor(Constants.ENCODER.
         * COUNTS_TO_INCHES);
         */

        fl.getEncoder().setPosition(0.0);
        fr.getEncoder().setPosition(0.0);
        bl.getEncoder().setPosition(0.0);
        br.getEncoder().setPosition(0.0);

        fl.setInverted(true);
        bl.setInverted(true);

        fr.setInverted(true);
        br.setInverted(true);

        SpeedControllerGroup left = new SpeedControllerGroup(fl, bl);
        SpeedControllerGroup right = new SpeedControllerGroup(fr, br);

        leftControl = new PIDController(2, 0.5, 1);
        rightControl = new PIDController(2, 0.5, 1);

        leftControl.setTolerance(10);
        rightControl.setTolerance(10);

        drive = new DifferentialDrive(left, right);

        setDefaultCommand(new JoystickDriveCommand());
    }

    @Override
    public void periodic() {
        // Reset the power to zero every loop
        // to prevent safety warning
        setPower(0, 0);
    }

    @Override
    public void disable() {
        setPower(0, 0);
    }

    public void setPower(double left, double right) {
        drive.tankDrive(left, right);
    }

    public void flushError() {
        leftControl.reset();
        rightControl.reset();
        fl.getEncoder().setPosition(0);
        fr.getEncoder().setPosition(0);
    }

    @Override
    String getSubsystemName() {
        return "Drivetrain";
    }

    public DifferentialDrive getDrive() {
        return drive;
    }

    public double leftEncoder() {
        return -fl.getEncoder().getPosition();
    }

    public double rightEncoder() {
        return fr.getEncoder().getPosition();
    }
}