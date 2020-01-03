package frc.robot.dash;

import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;;

// @Requires(group = "edu.wpi.first.shuffleboard", name = "Base", minVersion = "1.0.0")
@Description(group = "org.inspirerobotics.frc2020", name = "Dash", version = "0.1.0", summary = "FRC 4283 Custom Plugin")
public class Dash extends Plugin {

    @Override
    public void onLoad() {
        System.out.println("Dash loaded");
    }

    @Override
    public void onUnload() {
        System.out.println("Dash unloaded");
    }

    @Override
    public List<ComponentType> getComponents() {
        return Arrays.asList(
            WidgetType.forAnnotatedWidget(BooleanCircle.class),
            WidgetType.forAnnotatedWidget(AllianceBox.class)
            );
    }
}