package frc.robot.dash;

import java.util.Arrays;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import edu.wpi.first.shuffleboard.api.prefs.Group;
import edu.wpi.first.shuffleboard.api.prefs.Setting;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;

/**
 * A non-editable label on the dashboard. This widget also allows the user to
 * change the font size and font familt (see below)
 * <p>
 * The properties are listed below:
 * <ul>
 * <li><b>Font size</b>: The size of font to use
 * <li><b>Font family</b>: The family of font to use
 * <ul>
 */
@Description(name = "Simple Text", dataTypes = String.class)
@ParametrizedController("SimpleText.fxml")
public class SimpleText extends SimpleAnnotatedWidget<String> {

    @FXML
    private Pane root;

    @FXML
    private Label textLabel;

    private final Property<Integer> fontSize = new SimpleObjectProperty<>(this, "fontSize", 32);
    private final Property<String> fontFamiy = new SimpleObjectProperty<>(this, "fontFamily",
            "Arial");

    @FXML
    private void initialize() {
        textLabel.textProperty().bind(Bindings.createStringBinding(this::getText, dataProperty()));
        textLabel.fontProperty()
                .bind(Bindings.createObjectBinding(this::createFont, fontFamiy, fontSize));
    }

    private String getText() {
        String data = getData();

        return data == null ? "" : data;
    }

    private Font createFont() {
        return Font.font(fontFamiy.getValue(), fontSize.getValue());
    }

    @Override
    public Pane getView() {
        return root;
    }

    @Override
    public List<Group> getSettings() {
        return Arrays.asList(Group.of("Font",
                Setting.of("Font size", "The size of the font", fontSize, Integer.class),
                Setting.of("Font family", "The family of the font", fontFamiy, String.class)));
    }
}