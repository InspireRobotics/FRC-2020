package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hardware;
import frc.robot.subsystems.Hopper;

public class HopperLoopCommand extends CommandBase {

    private final Hopper hopper;

    public HopperLoopCommand() {
        hopper = Hardware.hopper;
    }

    @Override
    public void execute() {
        hopper.runHopper();
    }

    @Override
    public void end(boolean interrupted) {
        hopper.disable();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
