package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.Robot;

/**
 * The drivetrain on the robot.
 * 
 * This is responsible for managing the state of the drivetrain, including
 * encoder values (velocity, position, etc.)
 */
public class Drivetrain extends SubsystemBase {

    private MecanumDrive drive;

    @Override
    public void init(Robot robot) {
        var fl = new Spark(0);
        var fr = new Spark(1);
        var bl = new Spark(2);
        var br = new Spark(3);

        drive = new MecanumDrive(fl, bl, fr, br);
    }

    @Override
    public void periodic() {
        // Reset the power to zero every loop
        // to prevent safety warning
        drive.driveCartesian(0, 0, 0);
    }

    @Override
    public void disable() {
        drive.driveCartesian(0, 0, 0);
    }

    @Override
    String getSubsystemName() {
        return "Drivetrain";
    }

    public MecanumDrive getDrive() {
        return drive;
    }
}