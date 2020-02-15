package frc.robot.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * The primary autonomous logic
 */
public class AutoCommand extends SequentialCommandGroup {
    public AutoCommand() {
        addCommands(new DistanceDriveCommand(-20), new AlignCommand(),
                // TODO: Replace AlignCommand with ScoreCommand
                new DistanceDriveCommand(-50));
    }
}
