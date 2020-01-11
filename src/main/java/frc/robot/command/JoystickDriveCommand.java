package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Hardware;

/**
 * This is the default driving command. It takes input from the joysticks and
 * then sets the power to the motors
 */
public class JoystickDriveCommand extends CommandBase {

    public JoystickDriveCommand() {
        addRequirements(Hardware.drivetrain);
    }

    @Override
    public void execute() {
        var joystick = Constants.Joysticks.drive;

        var left = joystick.getRawAxis(1);
        var right = joystick.getRawAxis(5);

        Hardware.drivetrain.setPower(left, right);
    }

}