package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;

import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Responsible for managing the flywheels which launch the ball. Must work in
 * conjunction with the hopper system in order to "shoot" a ball.
 */
public class Shooter extends SubsystemBase {

    private PWMVictorSPX leftWheel;
    private PWMVictorSPX rightWheel;

    /**
     * Responsible for getting the flywheel motors and initializing parameters.
     * 
     * @param robot
     *            The robot instance.
     */
    @Override
    void init(Robot robot) {
        leftWheel = new PWMVictorSPX(Constants.CAN.SHOOT_L);
        rightWheel = new PWMVictorSPX(Constants.CAN.SHOOT_R);

        rightWheel.setInverted(true);
    }

    /**
     * Disables the flywheels by cutting power to them; this does NOT mean they will
     * immediately stop!
     */
    @Override
    void disable() {
        leftWheel.set(0);
        rightWheel.set(0);
    }

    /**
     * Resets power output in accordance with safety regulations.
     */
    @Override
    public void periodic() {
        leftWheel.set(0);
        rightWheel.set(0);
    }

    /**
     * Sets the percentage of maximum power to be delivered to the flywheels.
     * 
     * @param power
     *            Power percentage to set the flywheels to.
     */
    public void setPower(double power) {
        leftWheel.set(power);
        rightWheel.set(power);
    }

    /**
     * Gets the name of the subsystem.
     * 
     * @return The name of the subsystem as a String (always "Shooter")
     */
    @Override
    String getSubsystemName() {
        return "Shooter";
    }
}
