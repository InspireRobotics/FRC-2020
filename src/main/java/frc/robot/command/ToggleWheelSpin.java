package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hardware;

public class ToggleWheelSpin extends CommandBase {

    public ToggleWheelSpin() {
        addRequirements(Hardware.wheelSpinner);
    }

    @Override
    public void initialize() {
        Hardware.wheelSpinner.toggle();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
