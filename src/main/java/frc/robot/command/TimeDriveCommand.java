package frc.robot.command;

import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.subsystems.Hardware;

/**
 * Drives straight for a number of seconds. Used for debugging commands
 * 
 * Note: This should not be used for exact driving!
 */
public class TimeDriveCommand extends WaitCommand {

    public TimeDriveCommand(double seconds) {
        super(seconds);

        addRequirements(Hardware.drivetrain);
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }

    @Override
    public void execute() {
        Hardware.drivetrain.setPower(.3, .3);
    }

    @Override
    public void end(boolean interrupted) {
        Hardware.drivetrain.disable();
    }

}