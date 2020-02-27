package frc.robot;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.command.*;
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

    private Compressor compressor;
    // private Dashboard dashboard;

    @Override
    public void robotInit() {
        LiveWindow.disableAllTelemetry();

        System.out.println("Robot Init!");

        resetTime();

        hardware = new Hardware();
        hardware.init(this);
        hardware.disable();

        new JoystickButton(Constants.Joysticks.aux, 1).whenPressed(new HopperLoopCommand());
        new JoystickButton(Constants.Joysticks.aux, 2).whenPressed(new ShootCommand(1000));
        new JoystickButton(Constants.Joysticks.aux, 3).whenPressed(new ShootSpinUp(3000));

        new JoystickButton(Constants.Joysticks.drive, 3).whenPressed(new ToggleIntake());
        new JoystickButton(Constants.Joysticks.drive, 4).whenPressed(new ToggleWheelSpin());

        compressor = new Compressor(1);
        compressor.setClosedLoopControl(true);
        compressor.start();

        // dashboard = new Dashboard(this);
    }

    @Override
    public void robotPeriodic() {
        // dashboard.update();
    }

    @Override
    public void autonomousInit() {
        System.out.println("Auto Init!");

        // dashboard.autonomousInit();
        resetTime();

        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().schedule(new AutoCommand());
        CommandScheduler.getInstance().schedule(new ParallelDeadlineGroup(new AutoCommand()));
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        System.out.println("TeleOp Init!");

        // dashboard.teleopInit();
        resetTime();

        CommandScheduler.getInstance().cancelAll();
        Hardware.hopper.resetBallCount();
        compressor.start();
        CommandScheduler.getInstance().schedule(new HopperLoopCommand());
    }

    @Override
    public void teleopPeriodic() {
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

    // /**
    // * @see frc.robot.dashboard.Dashboard
    // */
    // public Dashboard getDashboard() {
    // return dashboard;
    // }

    /**
     * @see frc.robot.subsystems.Hardware
     */
    public Hardware getHardware() {
        return hardware;
    }
}
