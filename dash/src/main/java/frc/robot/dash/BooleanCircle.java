package frc.robot.dash;

import java.util.List;
import java.util.Arrays;

import edu.wpi.first.shuffleboard.api.prefs.Group;
import edu.wpi.first.shuffleboard.api.prefs.Setting;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

@Description(name = "Boolean Circle", dataTypes = Boolean.class)
@ParametrizedController("BooleanCircleWidget.fxml") 
public class BooleanCircle extends SimpleAnnotatedWidget<Boolean> {

    @FXML
    private Pane root;

    private final Property<Color> trueColor = new SimpleObjectProperty<>(this, "colorWhenTrue", Color.LAWNGREEN);
    private final Property<Color> falseColor = new SimpleObjectProperty<>(this, "colorWhenFalse", Color.DARKRED);

    @FXML
    private void initialize() {
        root.backgroundProperty().bind(Bindings.createObjectBinding(() -> createSolidColorBackground(getColor()),
                dataProperty(), trueColor, falseColor));
    }

    @Override
    public List<Group> getSettings() {
        return Arrays.asList(Group.of("Colors",
                Setting.of("Color when true", "The color to use when the value is `true`", trueColor, Color.class),
                Setting.of("Color when false", "The color to use when the value is `false`", falseColor, Color.class)));
    }

    @Override
    public Pane getView() {
        return root;
    }

    private Color getColor() {
        final Boolean data = getData();
        if (data == null) {
            return Color.ORANGE;
        }

        if (data) {
            return trueColor.getValue();
        } else {
            return falseColor.getValue();
        }
    }

    private Background createSolidColorBackground(Color color) {
        return new Background(new BackgroundFill(color, new CornerRadii(25, true), null));
    }
}