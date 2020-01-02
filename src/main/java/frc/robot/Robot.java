package frc.robot;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import edu.wpi.first.wpilibj.TimedRobot;

import frc.robot.dashboard.Dashboard;

/**
 * The main class for the program. This manages the commands, dashboard, and
 * hardware.
 */
public class Robot extends TimedRobot {

    /**
     * The time the last period change occured.
     * 
     * This is reset when the robot boots, the robot is enabled, or when the robot
     * is disabled.
     */
    private LocalTime startTime = LocalTime.now();
    private Dashboard dashboard;

    @Override
    public void robotInit() {
        System.out.println("Robot Init!");

        resetTime();

        dashboard = new Dashboard(this);
    }

    @Override
    public void robotPeriodic() {
        dashboard.update();
    }

    @Override
    public void autonomousInit() {
        System.out.println("Auto Init!");

        dashboard.autonomousInit();
        resetTime();
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        System.out.println("TeleOp Init!");

        dashboard.teleopInit();
        resetTime();
    }

    @Override
    public void teleopPeriodic() {
        super.teleopPeriodic();
    }

    @Override
    public void disabledInit() {
        System.out.println("Robot Disabled!");
        resetTime();
    }

    @Override
    public void disabledPeriodic() {

    }

    /**
     * Resets startTime to the current time.
     */
    private void resetTime() {
        startTime = LocalTime.now();
    }

    /**
     * @return how long, in seconds, between the start time and the current time
     * @see Robot#startTime
     */
    public long getRobotTime() {
        return ChronoUnit.SECONDS.between(startTime, LocalTime.now());
    }
}
