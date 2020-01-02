package frc.robot.dashboard;

import java.util.Map;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

import frc.robot.Robot;

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
        fmsWidget = tab.add("FMS Status", DriverStation.getInstance().isFMSAttached()).withSize(3, 1).withPosition(0,
                1);

        dsWidget = tab.add("DS Status", DriverStation.getInstance().isDSAttached()).withSize(3, 1).withPosition(3, 1);

        allianceWidget = tab.add("Alliance", isBlueDS())
                .withProperties(Map.of("Color when true", "#0000FF", "Color when false", "#FF5555")).withSize(6, 3)
                .withPosition(0, 2);

        matchNumber = tab.add("Match", "Match " + DriverStation.getInstance().getMatchNumber()).withSize(6, 1)
                .withPosition(0, 0);

        robotTime = tab.add("Robot Time", Long.toString(robot.getRobotTime())).withSize(5, 5).withPosition(6, 0);
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

class AutoTab extends DashboardTab {
    private SimpleWidget robotTime;

    @Override
    void init(Robot robot) {
        robotTime = tab.add("Robot Time", Long.toString(robot.getRobotTime())).withSize(2, 1).withPosition(0, 0);
    }

    @Override
    void update(Robot robot) {
        robotTime.getEntry().setString(Long.toString(robot.getRobotTime()));
    }

    @Override
    String getName() {
        return "Auto";
    }
}

class TeleOpTab extends DashboardTab {
    private SimpleWidget robotTime;

    @Override
    void init(Robot robot) {
        robotTime = tab.add("Robot Time", Long.toString(robot.getRobotTime())).withSize(2, 1).withPosition(0, 0);
    }

    @Override
    void update(Robot robot) {
        robotTime.getEntry().setString(Long.toString(robot.getRobotTime()));
    }

    @Override
    String getName() {
        return "TeleOp";
    }
}