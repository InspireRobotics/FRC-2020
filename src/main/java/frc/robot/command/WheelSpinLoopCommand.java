package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hardware;
import frc.robot.subsystems.SpinTheWheel;

public class WheelSpinLoopCommand extends CommandBase {
    private final SpinTheWheel spinTheWheel;

    public WheelSpinLoopCommand() {
        spinTheWheel = Hardware.wheelSpinner;
    }

    @Override
    public void execute() {
        spinTheWheel.run();
    }

    @Override
    public void end(boolean interrupted) {
        spinTheWheel.disable();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
