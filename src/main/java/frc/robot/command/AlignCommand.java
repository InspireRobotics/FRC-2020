package frc.robot.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Hardware;

public class AlignCommand extends CommandBase {

    double pos;
    long stableTime;
    boolean isStable;

    public AlignCommand() {
        addRequirements(Hardware.drivetrain);
    }

    @Override
    public void execute() {
        pos = SmartDashboard.getNumber("Contour X", 320);
        if (pos > 315 && pos < 325) {
            Hardware.drivetrain.disable();
        } else {
            double left = -Math.max(Math.min(Math.abs((pos - 320) / 320), Constants.POWER.AUTO_MAX), Constants.POWER.AUTO_MIN)
                    * Math.signum(pos - 320);
            double right = Math.max(Math.min(Math.abs((pos - 320) / 320), Constants.POWER.AUTO_MAX), Constants.POWER.AUTO_MIN)
                    * Math.signum(pos - 320);
            Hardware.drivetrain.setPower(left, right);
        }
    }

    @Override
    public boolean isFinished() {
        if (pos > 315 && pos < 325) {
            if (!isStable) {
                isStable = true;
                stableTime = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - stableTime > 1000){
                System.out.println("Aligned!");
                return true;
            }
        } else {
            isStable = false;
        }
        return false;
    }
}
