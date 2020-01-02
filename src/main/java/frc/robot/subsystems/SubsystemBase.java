package frc.robot.subsystems;

import frc.robot.Robot;

/**
 * The base class for all subsystems. This is very similar to
 * {@link edu.wpi.first.wpilibj2.command.SubsystemBase} but with a few custom
 * changes.
 */
public abstract class SubsystemBase extends edu.wpi.first.wpilibj2.command.SubsystemBase {

    SubsystemBase() {
        var name = getSubsystemName();

        setName(name);
        setSubsystem(name);
    }

    abstract void init(Robot robot);

    abstract void disable();

    public abstract void periodic();

    abstract String getSubsystemName();
}