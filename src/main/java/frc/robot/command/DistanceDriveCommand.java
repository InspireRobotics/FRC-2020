package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;

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
        targetLeft = Hardware.drivetrain.getFl().getEncoder().getPosition() + inches;
        targetRight = Hardware.drivetrain.getFr().getEncoder().getPosition() + inches;

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
                .atan(Hardware.drivetrain.getFl().getEncoder().getPosition() - targetLeft)
                / (Math.PI / 2);
        double powerRight = Math
                .atan(Hardware.drivetrain.getFr().getEncoder().getPosition() - targetRight)
                / (Math.PI / 2);

        powerLeft = Math.abs(powerLeft) > POWER.AUTO_MAX ? POWER.AUTO_MAX * Math.signum(powerLeft)
                : powerLeft;
        powerRight = Math.abs(powerRight) > POWER.AUTO_MAX
                ? POWER.AUTO_MAX * Math.signum(powerRight)
                : powerRight;

        powerLeft = Math.abs(powerLeft) < POWER.AUTO_MIN && Math
                .abs(targetLeft - Hardware.drivetrain.getFl().getEncoder().getPosition()) < ERROR
                        ? POWER.AUTO_MIN * Math.signum(powerLeft)
                        : powerLeft;
        powerRight = Math.abs(powerRight) < POWER.AUTO_MIN && Math
                .abs(targetRight - Hardware.drivetrain.getFr().getEncoder().getPosition()) < ERROR
                        ? POWER.AUTO_MIN * Math.signum(powerRight)
                        : powerRight;

        Hardware.drivetrain.setPower(powerLeft, powerRight);
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Reached target!");
        }
        Hardware.drivetrain.disable();
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