package frc.robot.dash;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

@Description(group = "org.inspirerobotics.frc2020", name = "Dash", version = "0.1.1", summary = "FRC 4283 Custom Plugin")
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
    @SuppressWarnings("rawtypes")
    public List<ComponentType> getComponents() {
        return Arrays.asList(WidgetType.forAnnotatedWidget(BooleanCircle.class),
                WidgetType.forAnnotatedWidget(AllianceBox.class),
                WidgetType.forAnnotatedWidget(SimpleText.class),
                WidgetType.forAnnotatedWidget(BallCounter.class));
    }
}