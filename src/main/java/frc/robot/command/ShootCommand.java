package frc.robot.command;

import frc.robot.subsystems.Hardware;

public class ShootCommand extends TimerCommand {

    public ShootCommand(long ms) {
        super(ms);
        addRequirements(Hardware.shooter, Hardware.hopper);
    }

    @Override
    public void initialize() {
        super.initialize();
        Hardware.hopper.removeBall();
    }

    @Override
    public void execute() {
        Hardware.shooter.setPower(1);
    }

    @Override
    public void end(boolean interrupted) {
        Hardware.shooter.setPower(0);
    }
}
