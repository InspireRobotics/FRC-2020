package frc.robot.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Combines the vision target alignment command with shoot to score balls into
 * the generator
 */
public class ScoreCommand extends SequentialCommandGroup {
    public ScoreCommand() {
        addCommands(new AlignCommand(), new ShootCommand(2000));
    }
}
