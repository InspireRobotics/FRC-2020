package frc.robot.dash;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import edu.wpi.first.shuffleboard.api.prefs.Group;
import edu.wpi.first.shuffleboard.api.prefs.Setting;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;

@Description(name = "Hopper Box", dataTypes = Number.class)
@ParametrizedController("HopperBox.fxml")
public class HopperBox extends SimpleAnnotatedWidget<Number> {

    private static final Image ball0 = loadImage("ball0.png");
    private static final Image ball1 = loadImage("ball1.png");
    private static final Image ball2 = loadImage("ball2.png");
    private static final Image ball3 = loadImage("ball3.png");
    private static final Image ball4 = loadImage("ball4.png");
    private static final Image ball5 = loadImage("ball5.png");

    private static final Image[] images =
         new Image[]{ball0, ball1, ball2, ball3, ball4, ball5};

    private final Property<Double> imageScale = new SimpleObjectProperty<>(this, "imageScale", 4.0);

    @FXML
    private Pane root;

    @FXML 
    private ImageView image; 

    @FXML
    private void initialize() {
        image.imageProperty()
            .bind(Bindings.createObjectBinding(this::getImage, dataProperty()));

        image.scaleXProperty().bind(Bindings.createObjectBinding(() -> imageScale.getValue(), imageScale));
        image.scaleYProperty().bind(Bindings.createObjectBinding(() -> imageScale.getValue(), imageScale));
    }

    private Image getImage(){
        int data = dataProperty().getValue().intValue();

        if(data >= 0 && data < images.length){
            return images[data];
        }

        return null;
    }

    @Override
    public List<Group> getSettings() {
        return Arrays.asList(Group.of("Generic",
            Setting.of("Image scale", "The scale of the image", imageScale, Double.class))
        );
    }

    public static Image loadImage(String path){
        try{
            return new Image(HopperBox.class.getResource(path).toURI().toString());
        }catch(URISyntaxException | IllegalArgumentException e) {
            System.out.println("Faild to load the ball image: " + path);

            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Pane getView() {
        return root;
    }
}