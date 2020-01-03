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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

@Description(name = "Simple Text", dataTypes = String.class)
@ParametrizedController("SimpleText.fxml")
public class SimpleText extends SimpleAnnotatedWidget<String> {

    @FXML
    private Pane root;

    @FXML
    private Label textLabel;

    private final Property<Integer> fontSize = new SimpleObjectProperty<>(this, "fontSize", 32);
    private final Property<String> fontFamiy = new SimpleObjectProperty<>(this, "fontFamily", "Arial");

    @FXML
    private void initialize() {
        textLabel.textProperty().bind(Bindings.createStringBinding(this::getText, dataProperty()));
        textLabel.fontProperty().bind(Bindings.createObjectBinding(this::createFont, fontFamiy, fontSize));
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