package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hardware;

public class ShootSpinUp extends CommandBase {

    private long start;
    private long time;

    public ShootSpinUp(long ms) {
        time = ms;

        addRequirements(Hardware.shooter);
    }

    @Override
    public void initialize() {
        start = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        Hardware.shooter.setPower(1);
    }

    @Override
    public void end(boolean interrupted) {
        Hardware.shooter.setPower(0);
    }

    @Override
    public boolean isFinished() {
        return (start - System.currentTimeMillis() + time) < 0;
    }
}
