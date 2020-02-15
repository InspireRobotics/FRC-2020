package frc.robot.dash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import edu.wpi.first.shuffleboard.api.prefs.Group;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;

@Description(name = "BallCounter", dataTypes = Integer.class)
@ParametrizedController("BallCounter.fxml")
public class BallCounter extends SimpleAnnotatedWidget<Number> {

    @FXML
    private Pane root;

    @FXML
    private ImageView image;

    private final ArrayList<Image> ballMeter = new ArrayList<>();

    @FXML
    private void initialize() {
        image.imageProperty()
                .bind(Bindings.createObjectBinding(this::getCurrentImage, dataProperty()));
        for (int i = 0; i <= 5; i++) {
            ballMeter.add(new Image("Assets/Images/Ball Gauge/Ball_Gauge" + i + ".png"));
        }
    }

    @Override
    public List<Group> getSettings() {
        return Collections.emptyList();
    }

    @Override
    public Pane getView() {
        return root;
    }

    private Image getCurrentImage() {
        int data = dataProperty().getValue().intValue();
        return ballMeter.get(data);
    }
}