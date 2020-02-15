package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Hardware;

/**
 * Rotates the color wheel to complete stage 2
 */
public class PositionCommand extends CommandBase {

    int rotCount = 0;
    Constants.COLORS startColor;
    boolean onStart = true;

    public PositionCommand() {
        addRequirements(Hardware.wheelSpinner);
    }

    @Override
    public void initialize() {
        startColor = Hardware.wheelSpinner.getColor();
    }

    @Override
    public void execute() {
        Hardware.wheelSpinner.raise();
        if (startColor == Hardware.wheelSpinner.getColor()) {
            if (!onStart) {
                rotCount++;
                onStart = true;
            }
        } else {
            onStart = false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        Hardware.wheelSpinner.lower();
    }

    @Override
    public boolean isFinished() {
        return rotCount > 3;
    }
}
