package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

import frc.robot.Constants;
import frc.robot.Constants.POWER;
import frc.robot.subsystems.Hardware;

/**
 * Drives straight for a number of inches. Used for debugging commands
 *
 * Note: This should not be used for exact driving! Testing has shown
 * significant error upwards of ±20% over large distances.
 */
public class DistanceDriveCommand extends CommandBase {

    private final double ERROR = 0.3;

    private final double targetLeft;
    private final double targetRight;

    private long onTargetTime = 0;
    private boolean onTarget = false;

    public DistanceDriveCommand(double inches) {
        targetLeft = Hardware.drivetrain.leftEncoder()
                * Constants.ENCODER.COUNTS_TO_INCHES_DRIVETRAIN + inches;
        targetRight = Hardware.drivetrain.rightEncoder()
                * Constants.ENCODER.COUNTS_TO_INCHES_DRIVETRAIN + inches;

        System.out.println(String.format("Targets: [L:%f.1, R:%f.1]", targetLeft, targetRight));

        addRequirements(Hardware.drivetrain);
    }

    /**
     * Always returns false.
     *
     * @return A condition which reports if the command runs while the robot is
     *         disabled.
     */
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }

    /**
     * Performs the main execution loop to drive the robot.
     */
    @Override
    public void execute() {

        double powerLeft = Math
                .atan(Hardware.drivetrain.leftEncoder()
                        * Constants.ENCODER.COUNTS_TO_INCHES_DRIVETRAIN - targetLeft)
                / (Math.PI / 2);
        double powerRight = Math
                .atan(Hardware.drivetrain.rightEncoder()
                        * Constants.ENCODER.COUNTS_TO_INCHES_DRIVETRAIN - targetRight)
                / (Math.PI / 2);

        powerLeft = MathUtil.clamp(Math.abs(powerLeft), POWER.AUTO_MIN, POWER.AUTO_MAX)
                * Math.signum(powerLeft);
        powerRight = MathUtil.clamp(Math.abs(powerRight), POWER.AUTO_MIN, POWER.AUTO_MAX)
                * Math.signum(powerRight);

        Hardware.drivetrain.setPower(powerLeft, powerRight);
    }

    /**
     * Stops the drivetrain and resets the error accumulated in the PID loop, and
     * prints a message if not interrupted.
     *
     * @param interrupted
     *            If the robot was interrupted.
     */
    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Reached target!");
        }
        Hardware.drivetrain.disable();
    }

    /**
     * Checks if the robot is finished by calculating the error from the target, and
     * checking if the robot is within a tolerable range.
     *
     * @return If the robot is finished or not
     */
    @Override
    public boolean isFinished() {
        if (Math.abs(
                Hardware.drivetrain.leftEncoder() * Constants.ENCODER.COUNTS_TO_INCHES_DRIVETRAIN
                        - targetLeft) < ERROR
                && Math.abs(Hardware.drivetrain.rightEncoder()
                        * Constants.ENCODER.COUNTS_TO_INCHES_DRIVETRAIN - targetRight) < ERROR) {
            if (!onTarget) {
                onTargetTime = System.currentTimeMillis();
                onTarget = true;
            }
        } else {
            onTarget = false;
        }
        return onTarget && Math.abs(System.currentTimeMillis() - onTargetTime) > 500;
    }
}