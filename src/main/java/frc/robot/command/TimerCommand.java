package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Ends after a certain amount of time has elapsed. Used as the base for other commands or in command groups.
 */
public class TimerCommand extends CommandBase {

    private long startTime;
    private long duration;

    public TimerCommand(long ms) {
        duration = ms;
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() > startTime + duration;
    }
}
