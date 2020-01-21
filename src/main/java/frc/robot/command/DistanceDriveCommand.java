package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.Constants.POWER;
import frc.robot.subsystems.Hardware;

/**
 * Drives straight for a number of inches. Used for debugging commands
 * 
 * Note: This should not be used for exact driving!
 */
public class DistanceDriveCommand extends CommandBase {

    private final double ERROR = 0.3;

    private final double targetLeft;
    private final double targetRight;

    private long onTargetTime = 0;
    private boolean onTarget = false;

    public DistanceDriveCommand(double inches) {
        targetLeft = Hardware.drivetrain.getFl().getEncoder().getPosition() * Constants.ENCODER.COUNTS_TO_INCHES + inches;
        targetRight = Hardware.drivetrain.getFr().getEncoder().getPosition() * Constants.ENCODER.COUNTS_TO_INCHES + inches;

        System.out.println(String.format("Targets: [L:%f.1, R:%f.1]", targetLeft, targetRight));

        addRequirements(Hardware.drivetrain);
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }

    @Override
    public void execute() {

        double powerLeft = Math
                .atan(Hardware.drivetrain.getFl().getEncoder().getPosition() * Constants.ENCODER.COUNTS_TO_INCHES - targetLeft)
                / (Math.PI / 2);
        double powerRight = Math
                .atan(Hardware.drivetrain.getFr().getEncoder().getPosition() * Constants.ENCODER.COUNTS_TO_INCHES - targetRight)
                / (Math.PI / 2);

        powerLeft = MathUtil.clamp(Math.abs(powerLeft), POWER.AUTO_MIN, POWER.AUTO_MAX) * Math.signum(powerLeft) * Constants.ENCODER.MAX_RPM;
        powerRight = MathUtil.clamp(Math.abs(powerRight), POWER.AUTO_MIN, POWER.AUTO_MAX) * Math.signum(powerRight) * Constants.ENCODER.MAX_RPM;

        Hardware.drivetrain.setVelocity(powerLeft, powerRight);
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Reached target!");
        }
        Hardware.drivetrain.disable();
        Hardware.drivetrain.flushError();
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(Hardware.drivetrain.getFl().getEncoder().getPosition() - targetLeft) < ERROR
                && Math.abs(Hardware.drivetrain.getFr().getEncoder().getPosition()
                        - targetRight) < ERROR) {
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