package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Responsible for managing the flywheels which launch the ball. Must work in
 * conjunction with the hopper system in order to "shoot" a ball.
 */
public class Shooter extends SubsystemBase {

    private VictorSPX leftWheel;
    private VictorSPX rightWheel;

    /**
     * Responsible for getting the flywheel motors and initializing parameters.
     * 
     * @param robot
     *            The robot instance.
     */
    @Override
    void init(Robot robot) {
        leftWheel = new VictorSPX(Constants.CAN.SHOOT_L);
        rightWheel = new VictorSPX(Constants.CAN.SHOOT_R);

        rightWheel.setInverted(true);
    }

    /**
     * Disables the flywheels by cutting power to them; this does NOT mean they will
     * immediately stop!
     */
    @Override
    void disable() {
        setPower(0);
    }

    /**
     * Resets power output in accordance with safety regulations.
     */
    @Override
    public void periodic() {
        setPower(0);
    }

    /**
     * Sets the percentage of maximum power to be delivered to the flywheels.
     * 
     * @param power
     *            Power percentage to set the flywheels to.
     */
    public void setPower(double power) {
        leftWheel.set(ControlMode.PercentOutput, power);
        rightWheel.set(ControlMode.PercentOutput, -power);
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
