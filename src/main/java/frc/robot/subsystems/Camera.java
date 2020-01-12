package frc.robot.subsystems;

import edu.wpi.cscore.*;
import frc.robot.Robot;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Size;

import java.util.Arrays;
import java.util.List;


import edu.wpi.cscore.*;

import frc.robot.Robot;

/**
<<<<<<< HEAD
 * The camera on the robot.
 * 
 * This is responsible for managing the camera and its output, including
 * any processing which may be involved (in this case OpenCV)
=======
 * The USB Camera on the shooter. This class is also responsible for setting up
 * the vision targetting proccessing.
>>>>>>> ad02909ad6b546f67bbd391f524ad753dad1ab94
 */
public class Camera extends SubsystemBase {

    private UsbCamera camera;
    private CvSink targetInput;
    private CvSource targetOutput;
    private MjpegServer driverFeed;
    private VisionProcessing processing;

    @Override
    public void init(Robot robot) {
        camera = new UsbCamera("Front Camera", 0); // Set up the input stream for the USB camera

        targetInput = new CvSink("Vision Target Processing"); // Set up the processing segment of
                                                              // the vision targeting pipeline
        targetInput.setSource(camera); // Connect the camera to the processing segment

        targetOutput = new CvSource("Vision Target Output",
                        VideoMode.PixelFormat.kMJPEG, 640, 480, 30);

        driverFeed = new MjpegServer("Baked Output", 1181);
        driverFeed.setSource(targetOutput);
    }

    @Override
    public void periodic() {

        if (camera == null){
            return;
        }

        Mat input = new Mat();
        targetInput.grabFrame(input);

        targetOutput.putFrame(processing.BasicProcessing(input));
    }

    @Override
    public void disable() {
        /*
         * As there are no moving parts, and there is not a great way to re-enable, this
         * does nothing
         */
    }

    @Override
    String getSubsystemName() {
        return "Camera";
    }
}


/**
 * The vision processing for the robot
 *
 * This is responsible for handling vision processing in an efficient manner for the camera.
 * It does this through final allocation of any needed Mat objects, which are reused for each method call.
 * However, this means any memory reserved by the class will be withheld until the class is de-allocated.
 */
class VisionProcessing {

    private final List<Mat> processingFrames = Arrays.asList(new Mat(), new Mat());
    private final Mat basicDilateKernal =
            Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3), new Point(1, 1));

    Mat BasicProcessing(Mat input) {
        Imgproc.bilateralFilter(input, processingFrames.get(0), 2, 4, 1);
        Imgproc.dilate(processingFrames.get(0), processingFrames.get(1), basicDilateKernal);

        return processingFrames.get(1).clone(); // Clone for thread-safety
    }
}