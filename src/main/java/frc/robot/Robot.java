package frc.robot;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.command.AutoCommand;
import frc.robot.dashboard.Dashboard;
import frc.robot.subsystems.Hardware;

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

    private Hardware hardware;
    private Dashboard dashboard;

    @Override
    public void robotInit() {
        LiveWindow.disableAllTelemetry();

        System.out.println("Robot Init!");

        resetTime();

        hardware = new Hardware();
        hardware.init(this);
        hardware.disable();

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

        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().schedule(new AutoCommand());
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        System.out.println("TeleOp Init!");

        dashboard.teleopInit();
        resetTime();

        CommandScheduler.getInstance().cancelAll();
        // CommandScheduler.getInstance().schedule(new ButtonShootCommand());
    }

    @Override
    public void teleopPeriodic() {
        Hardware.hopper.runHopper();
        Hardware.wheelSpinner.run();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        System.out.println("Robot Disabled!");
        resetTime();

        hardware.disable();
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void disabledPeriodic() {
        CommandScheduler.getInstance().run();
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

    /**
     * @see frc.robot.dashboard.Dashboard
     */
    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     * @see frc.robot.subsystems.Hardware
     */
    public Hardware getHardware() {
        return hardware;
    }
}
