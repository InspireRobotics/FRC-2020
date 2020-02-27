package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hardware;
import frc.robot.subsystems.Hopper;

public class ToggleIntake extends CommandBase {

    public ToggleIntake() {
        addRequirements(Hardware.hopper);
    }

    @Override
    public void initialize() {
        Hardware.hopper.toggleIntake();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
