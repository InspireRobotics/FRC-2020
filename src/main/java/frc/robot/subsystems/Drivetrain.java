package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import frc.robot.Robot;

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
        var fl = new Spark(0);
        var fr = new Spark(1);
        var bl = new Spark(2);
        var br = new Spark(3);

        var left = new SpeedControllerGroup(fl, bl);
        var right = new SpeedControllerGroup(fr, br);

        drive = new DifferentialDrive(left, right);
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

    public void setPower(double left, double right){
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