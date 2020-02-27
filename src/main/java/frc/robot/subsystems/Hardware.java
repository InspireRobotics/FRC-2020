package frc.robot.subsystems;

import java.util.Arrays;
import java.util.List;

import frc.robot.Robot;

/**
 * This class is responsible for managing all of the hardware on the robot.
 */
public class Hardware {

    /**
     * The drivetrain of the robot.
     */
    public static final Drivetrain drivetrain = new Drivetrain();
    /**
     * The shooter of the robot.
     */
    public static final Shooter shooter = new Shooter();
    /**
     * The collection system and hopper of the robot.
     */
    public static final Hopper hopper = new Hopper();
    /**
     * The wheel spinner of the robot.
     */
    public static final SpinTheWheel wheelSpinner = new SpinTheWheel();

    /**
     * A list of subsystems on the robot. All subsystems that should be run must be
     * added to this!
     */
    private final List<SubsystemBase> subsystems = Arrays.asList(drivetrain, hopper, shooter,
            wheelSpinner);

    /**
     * Initializes all of the subsystems on the robot
     */
    public void init(Robot robot) {
        subsystems.forEach(subsystem -> subsystem.init(robot));
    }

    /**
     * Disables all of the subsystems on the robot
     */
    public void disable() {
        subsystems.forEach(SubsystemBase::disable);
    }

    /**
     * The list of subsystems on the robot
     */
    public List<SubsystemBase> getSubsystems() {
        return subsystems;
    }

}