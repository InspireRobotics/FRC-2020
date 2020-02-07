package frc.robot.dash;

import edu.wpi.first.shuffleboard.api.prefs.Group;
import edu.wpi.first.shuffleboard.api.prefs.Setting;
import edu.wpi.first.shuffleboard.api.widget.ComplexAnnotatedWidget;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Description(name = "BallCounter", dataTypes = Image.class)
@ParametrizedController("BooleanCircleWidget.fxml")
public class BallCounter extends SimpleAnnotatedWidget<ImageView> {

    @FXML
    private Pane root;

    private final Property<ArrayList<Image>> ballMeter = new SimpleObjectProperty<>(this, "Ball meter states",
            new ArrayList<Image>());

    private int ballCount = 0;

    @FXML
    private void initialize() {
        for (int i = 0; i <= 5; i++) {
            ballMeter.getValue().add(new Image("Assets/Images/Ball Gauge/Ball_Gauge" + i + ".png"));
        }

        final ImageView view = new ImageView(getCurrentImage(ballCount));
        view.imageProperty().bind(Bindings.createObjectBinding(() -> getCurrentImage(ballCount)));

        root.getChildren().add(view);
    }

    @Override
    public List<Group> getSettings() {
        return Arrays.asList(Group.of("Ball Image Buffer",
                Setting.of("Ball image buffer", ballMeter)));
    }

    @Override
    public Pane getView() {
        return root;
    }

    private ImageView getImageView() {
        return getData();
    }

    private Image getCurrentImage(int ballCount) {
        return ballMeter.getValue().get(ballCount);
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }
}