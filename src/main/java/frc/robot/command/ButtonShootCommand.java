package frc.robot.command;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Hardware;

public class ButtonShootCommand extends CommandBase {
    public ButtonShootCommand() { addRequirements(Hardware.shooter); }

    @Override
    public void execute() {
        XboxController joystick = Constants.Joysticks.aux;
        if (joystick.getAButton()) {
            Hardware.shooter.setPower(1);
        }
    }
}
