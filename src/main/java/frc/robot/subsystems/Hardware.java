package frc.robot.subsystems;

import java.util.Arrays;
import java.util.List;

import frc.robot.Robot;

/**
 * This class is responsible for managing all of the hardware on the robot.
 */
public class Hardware {

    public static final Drivetrain drivetrain = new Drivetrain();

    /**
     * A list of subsystems on the robot. All subsystems that should be run must be
     * added to this!
     */
    private final List<SubsystemBase> subsystems = Arrays.asList(drivetrain); // Note:
                                                                                      // Camera not
                                                                                      // added
                                                                                      // because not
                                                                                      // on robot
                                                                                      // yet

    /**
     * Initializes all of the subsytems on the robot
     */
    public void init(Robot robot) {
        subsystems.forEach(subsystem -> subsystem.init(robot));
    }

    /**
     * Disables all of the subsytems on the robot
     */
    public void disable() {
        subsystems.forEach(SubsystemBase::disable);
    }

    /**
     * the list of subsystems on the robot
     */
    public List<SubsystemBase> getSubsystems() {
        return subsystems;
    }

}