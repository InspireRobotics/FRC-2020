package frc.robot.dashboard;

import java.util.Arrays;
import java.util.List;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import frc.robot.Robot;

/**
 * This manages all of the dashboard tabs, and handles switching the tabs when
 * the robot state changes.
 */
public class Dashboard {

    /**
     * The list of tabs that are initalized and updated
     */
    private final List<DashboardTab> tabs;
    private final Robot robot;

    private final DashboardTab preMatchTab = new PreMatchTab();
    private final DashboardTab autoTab = new AutoTab();
    private final DashboardTab teleOpTab = new TeleOpTab();

    public Dashboard(Robot robot) {
        this.robot = robot;

        this.tabs = Arrays.asList(preMatchTab, autoTab, teleOpTab);
        this.tabs.forEach(tab -> tab.init(robot));

        setTab(preMatchTab);
    }

    public void update() {
        tabs.forEach(tab -> tab.update(robot));
    }

    public void autonomousInit() {
        setTab(autoTab);
    }

    public void teleopInit() {
        setTab(teleOpTab);
    }

    /**
     * A more robust version of {@link Shuffleboard#selectTab(String)}
     * 
     * @param tab the tab Shuffleboard should switch to.
     */
    private void setTab(DashboardTab tab) {
        // Do this to force shuffleboard to select the right tab
        // If the user manually switches tabs, shuffleboard will still
        // think it is still on the old tab, and therefore does nothing. By
        // forcing it to go another tab, this makes sure it switches every time.
        Shuffleboard.selectTab("");
        Shuffleboard.selectTab(tab.getName());
    }
}
