package frc.robot.command;

import edu.wpi.first.wpilibj.XboxController;
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

    /**
     * The main execution loop.
     */
    @Override
    public void execute() {
        XboxController joystick = Constants.Joysticks.drive;

        double left = joystick.getRawAxis(1);
        double right = joystick.getRawAxis(5);

        left = Math.abs(left) < Constants.POWER.TELEOP_MIN ? 0 : left;
        right = Math.abs(right) < Constants.POWER.TELEOP_MIN ? 0 : right;

        Hardware.drivetrain.setPower(left * Constants.POWER.TELEOP_MAX,
                right * Constants.POWER.TELEOP_MAX);
    }

}