package frc.robot.dashboard;

import java.awt.*;
import java.util.Map;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.Robot;
import frc.robot.subsystems.Hardware;
import javafx.scene.effect.ImageInput;
import org.opencv.core.Mat;
import org.opencv.video.Video;

/**
 * The class that all tabs derive from.
 */
public abstract class DashboardTab {

    protected final ShuffleboardTab tab;

    public DashboardTab() {
        this.tab = Shuffleboard.getTab(getName());
    }

    /**
     * Initalizes all of the widgets, and sets their initial values
     */
    abstract void init(Robot robot);

    /**
     * This is called every update and should be used to update the values on the
     * dashboard
     */
    abstract void update(Robot robot);

    /**
     * The name of the tab to appear on Shuffleboard
     */
    abstract String getName();

}

class PreMatchTab extends DashboardTab {

    private SimpleWidget fmsWidget;
    private SimpleWidget dsWidget;
    private SimpleWidget allianceWidget;
    private SimpleWidget matchNumber;
    private SimpleWidget robotTime;

    @Override
    public void init(Robot robot) {
        fmsWidget = tab.add("FMS Status", DriverStation.getInstance().isFMSAttached())
                .withSize(3, 1).withPosition(0, 1);

        dsWidget = tab.add("DS Status", DriverStation.getInstance().isDSAttached()).withSize(3, 1)
                .withPosition(3, 1);

        allianceWidget = tab.add("Alliance", isBlueDS())
                .withProperties(Map.of("Color when true", "#0000FF", "Color when false", "#FF5555"))
                .withSize(6, 3).withPosition(0, 2).withWidget("Alliance Box");

        matchNumber = tab.add("Match", "Match " + DriverStation.getInstance().getMatchNumber())
                .withSize(6, 1).withPosition(0, 0).withWidget("Simple Text");

        robotTime = tab.add("Robot Time", Long.toString(robot.getRobotTime())).withSize(5, 5)
                .withPosition(6, 0).withWidget("Simple Text")
                .withProperties(Map.of("Font size", 72));
    }

    @Override
    public void update(Robot robot) {
        fmsWidget.getEntry().setBoolean(DriverStation.getInstance().isFMSAttached());
        dsWidget.getEntry().setBoolean(DriverStation.getInstance().isDSAttached());
        allianceWidget.getEntry().setBoolean(isBlueDS());
        matchNumber.getEntry().setString("Match " + DriverStation.getInstance().getMatchNumber());
        robotTime.getEntry().setString(Long.toString(robot.getRobotTime()));
    }

    private boolean isBlueDS() {
        return DriverStation.getInstance().getAlliance() == Alliance.Blue;
    }

    @Override
    public String getName() {
        return "PreMatch";
    }

}

/**
 * The base class for AutoTab and TeleOpTab.
 * 
 * It contains the basic widgets in both tabs such as subsystems, times, etc.
 */
abstract class HardwareTab extends DashboardTab {

    private SimpleWidget robotTime;
    private ShuffleboardLayout encoders;

    private NetworkTableEntry frontLeft;
    private NetworkTableEntry frontRight;

    @Override
    void init(Robot robot) {
        var subsystemLayout = tab.getLayout("Subsystems", BuiltInLayouts.kList).withSize(4, 4)
                .withPosition(0, 0);

        robot.getHardware().getSubsystems().forEach(subsystem -> {
            subsystemLayout.add(subsystem.getName(), subsystem);
        });

        tab.add("Drive", Hardware.drivetrain.getDrive()).withPosition(4, 0).withSize(4, 3);
        robotTime = tab.add("Robot Time", Long.toString(robot.getRobotTime())).withPosition(4, 3)
                .withSize(4, 1).withWidget("Simple Text");

        encoders = tab.getLayout("Encoders", BuiltInLayouts.kGrid).withSize(3, 2).withPosition(8,
                0);

        frontLeft = encoders.add("Front Left", 0.0).withSize(5, 1).withPosition(0, 0).getEntry();
        frontRight = encoders.add("Front Right", 0.0).withSize(5, 1).withPosition(0, 1).getEntry();

    }

    @Override
    void update(Robot robot) {
        robotTime.getEntry().setString(Long.toString(robot.getRobotTime()));
        frontLeft.setValue(Hardware.drivetrain.leftEncoder());
        frontRight.setValue(Hardware.drivetrain.rightEncoder());
    }
}

class AutoTab extends HardwareTab {

    @Override
    void init(Robot robot) {
        super.init(robot);
    }

    @Override
    void update(Robot robot) {
        super.update(robot);
    }

    @Override
    String getName() {
        return "Auto";
    }
}

class TeleOpTab extends HardwareTab {

    @Override
    void init(Robot robot) {
        super.init(robot);
    }

    @Override
    void update(Robot robot) {
        super.update(robot);
    }

    @Override
    String getName() {
        return "TeleOp";
    }
}