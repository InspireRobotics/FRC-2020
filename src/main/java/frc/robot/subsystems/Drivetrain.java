package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
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

    private DifferentialDrive drive;

    @Override
    public void init(Robot robot) {
        var fl = new Spark(Constants.CAN.DRIVE_FL);
        var fr = new Spark(Constants.CAN.DRIVE_FR);
        var bl = new Spark(Constants.CAN.DRIVE_BL);
        var br = new Spark(Constants.CAN.DRIVE_BR);

        var left = new SpeedControllerGroup(fl, bl);
        var right = new SpeedControllerGroup(fr, br);

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

    @Override
    String getSubsystemName() {
        return "Drivetrain";
    }

    public DifferentialDrive getDrive() {
        return drive;
    }
}