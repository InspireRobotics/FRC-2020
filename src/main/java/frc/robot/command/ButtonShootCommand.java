package frc.robot.command;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Hardware;

/**
 * Activates the shooter at the press of a button. Zoom!
 */
public class ButtonShootCommand extends CommandBase {
    public ButtonShootCommand() {
        addRequirements(Hardware.shooter);
    }

    /**
     * Checks if the aux driver is pressing A, and if so applies power to the
     * flywheels
     */
    @Override
    public void execute() {
        XboxController joystick = Constants.Joysticks.aux;
        if (joystick.getAButton()) {
            Hardware.shooter.setPower(1);
        }
    }

    /**
     * Stops the shooter in case of catastrophic failure.
     * 
     * @param interrupted
     *            If the command was interrupted mid-execution.
     */
    @Override
    public void end(boolean interrupted) {
        Hardware.shooter.setPower(0);
    }
}
