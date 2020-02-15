package frc.robot.command;

import java.io.IOException;

import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hardware;
import frc.robot.subsystems.Hopper;

/**
 * Picks up a ball using the pixycam.
 */
public class AutoPickup extends CommandBase {

    private Hopper hopper;
    private Drivetrain drivetrain;

    private byte startCount;

    public AutoPickup() {
        hopper = Hardware.hopper;
        drivetrain = Hardware.drivetrain;

        addRequirements(Hardware.drivetrain, Hardware.shooter);
    }

    /**
     * Initializes the command, updating the starting ball count.
     */
    @Override
    public void initialize() {
        startCount = hopper.ballCount();
    }

    /**
     * Drives towards the ball
     */
    @Override
    public void execute() {
        hopper.runIntake();
        try {
            Vector2d pos = hopper.ballPosition();
            double offset = (pos.x - Constants.PIXYCAM.CENTER_X) / Constants.PIXYCAM.CENTER_X;

            drivetrain.setPower(
                    MathUtil.clamp(Math.atan(offset) - Constants.POWER.AUTO_MIN,
                            -Constants.POWER.AUTO_MAX, Constants.POWER.AUTO_MAX),
                    MathUtil.clamp(-Math.atan(offset) - Constants.POWER.AUTO_MIN,
                            -Constants.POWER.AUTO_MAX, Constants.POWER.AUTO_MAX));
        } catch (IOException e) {

        }
    }

    /**
     * Ends the command, freeing the drivetrain and hopper, while signalling the
     * robot to brake.
     *
     * @param interrupted
     *            Indicates if the command was interrupted or not
     */
    @Override
    public void end(boolean interrupted) {
        drivetrain.disable();
    }

    /**
     * Check to see if we picked up a ball since initialization.
     *
     * @return If the command has reached an end condition.
     */
    @Override
    public boolean isFinished() {
        return hopper.ballCount() != startCount;
    }
}
