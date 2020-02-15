package frc.robot.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

import frc.robot.Constants;
import frc.robot.subsystems.Hardware;

/**
 * Aligns the robot with a vision target using the RaspberryPi's camera
 *
 *
 */
public class AlignCommand extends CommandBase {

    double pos;
    long stableTime;
    boolean isStable;

    public AlignCommand() {
        addRequirements(Hardware.drivetrain);
    }

    /**
     * Control loop to align robot to target; uses PID controller.
     */
    @Override
    public void execute() {
        pos = SmartDashboard.getNumber("Contour X", 320);
        // pos = NetworkTableInstance.getDefault().getEntry("Contour X").getDouble(320);
        if (pos > 315 && pos < 325) {
            Hardware.drivetrain.disable();
        } else {
            double left = -MathUtil.clamp(Math.abs((pos - 320) / 320), Constants.POWER.AUTO_MIN,
                    Constants.POWER.AUTO_MAX) * Math.signum(pos - 320);
            double right = MathUtil.clamp(Math.abs((pos - 320) / 320), Constants.POWER.AUTO_MIN,
                    Constants.POWER.AUTO_MAX) * Math.signum(pos - 320);
            Hardware.drivetrain.setPower(left, right);
        }
    }

    /**
     * Checks if the robot is aligned and is stable (aka so the robot doesn't
     * overshoot and finish
     * 
     * @return Condition indicating the execution loop is finished
     */
    @Override
    public boolean isFinished() {
        if (pos > 315 && pos < 325) {
            if (!isStable) {
                isStable = true;
                stableTime = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - stableTime > 1000) {
                System.out.println("Aligned!");
                return true;
            }
        } else {
            isStable = false;
        }
        return false;
    }

    /**
     * Preforms resets to ensure the drivetrain is ready to be used by other
     * commands.
     * 
     * @param interrupted
     *            - Indicates if the command was interrupted or ended normally.
     */
    @Override
    public void end(boolean interrupted) {
        Hardware.drivetrain.disable();
    }
}
