package frc.robot.dash;

import java.util.Arrays;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import edu.wpi.first.shuffleboard.api.prefs.Group;
import edu.wpi.first.shuffleboard.api.prefs.Setting;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;

/**
 * A widget to display the current alliance. It is basically a boolean box with
 * text displayed for true and false.
 * <p>
 * The properties are listed below:
 * <ul>
 * <li><b>Color when blue</b>: The text to show when the value is set to true
 * <li><b>Color when red</b>: The text to show when the value is set to false
 * <li><b>Text when blue</b>: The color to show when the value is set to true
 * <li><b>Text when red</b>: The color to show when the value is set to false
 * <ul>
 */
@Description(name = "Alliance Box", dataTypes = Boolean.class)
@ParametrizedController("AllianceBox.fxml")
public class AllianceBox extends SimpleAnnotatedWidget<Boolean> {

    @FXML
    private Pane root;

    @FXML
    private Label alliance;

    private final Property<String> blueText = new SimpleObjectProperty<>(this, "textWhenBlue",
            "Blue Alliance");
    private final Property<String> redText = new SimpleObjectProperty<>(this, "textWhenRed",
            "Red Alliance");

    private final Property<Color> blueColor = new SimpleObjectProperty<>(this, "colorWhenBlue",
            Color.BLUE);
    private final Property<Color> redColor = new SimpleObjectProperty<>(this, "colorWhenRed",
            Color.DARKRED);

    @FXML
    private void initialize() {
        root.backgroundProperty().bind(Bindings.createObjectBinding(
                () -> createSolidColorBackground(getColor()), dataProperty(), blueColor, redColor));

        alliance.textProperty().bind(Bindings.createStringBinding(this::getAllianceText,
                dataProperty(), blueText, redText));
    }

    String getAllianceText() {
        Boolean data = getData();

        if (data == null) {
            return "";
        } else {
            return getData() ? blueText.getValue() : redText.getValue();
        }
    }

    @Override
    public List<Group> getSettings() {
        return Arrays.asList(
                Group.of("Colors",
                        Setting.of("Color when blue", "The color to use when the value is `true`",
                                blueColor, Color.class),
                        Setting.of("Color when red", "The color to use when the value is `false`",
                                redColor, Color.class)),
                Group.of("Text",
                        Setting.of("Text when blue", "The text to use when the value is 'true'",
                                blueText, String.class),
                        Setting.of("Text when red", "The text to use when the value is 'false'",
                                redText, String.class)));
    }

    @Override
    public Pane getView() {
        return root;
    }

    private Color getColor() {
        final Boolean data = getData();
        if (data == null) {
            return Color.GRAY;
        }

        if (data) {
            return blueColor.getValue();
        } else {
            return redColor.getValue();
        }
    }

    private Background createSolidColorBackground(Color color) {
        return new Background(new BackgroundFill(color, null, null));
    }
}