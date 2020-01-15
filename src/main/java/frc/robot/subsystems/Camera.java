package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.CameraServer;

import frc.robot.Robot;

/**
 * The camera on the robot.
 * 
 * This is responsible for managing the camera and its output, including any
 * processing which may be involved (in this case OpenCV).
 */
public class Camera extends SubsystemBase {

    private UsbCamera camera;
    private CvSink targetInput;
    private CvSource targetOutput;

    @Override
    public void init(Robot robot) {
        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 640, 480, 120);
        camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        targetInput = new CvSink("Pipeline");
        targetInput.setSource(camera);
        targetOutput = CameraServer.getInstance().putVideo("Output", 640, 480);

        new Thread(() -> {
            Mat source = new Mat();
            Mat output;
            ArrayList<MatOfPoint> bakedOutput;

            while(!Thread.interrupted()) {
                if (targetInput.grabFrame(source) == 0) {
                    targetOutput.notifyError(targetInput.getError());
                    continue;
                }

                output = VisionProcessing.BasicProcessing(source);
                /*If this works, I guess I did it?
                bakedOutput = VisionProcessing.BakedProcessing(source);
                //TODO: Figure out how to convert ArrayList<MatOfPoint> to an image*/
                targetOutput.putFrame(output);
            }
        }).start();
    }

    @Override
    public void periodic() {

        /*if (camera == null) {
            return;
        }
         * Mat input = new Mat(); targetInput.grabFrame(input);
         * 
         * targetOutput.putFrame(input);
         */
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
 * This is responsible for handling vision processing in an efficient manner for
 * the camera. It does this through final allocation of any needed Mat objects,
 * which are reused for each method call. However, this means any memory
 * reserved by the class will be withheld until the class is de-allocated.
 */
class VisionProcessing {

    protected static final List<Mat> processingFrames = Arrays.asList(new Mat(), new Mat());
    protected static final Mat basicDilateKernel = Imgproc
            .getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3), new Point(1, 1));
    protected static final GripPipeline pipe = new GripPipeline();

    protected static Mat BasicProcessing(Mat input) {
        Imgproc.bilateralFilter(input, processingFrames.get(0), 2, 4, 1);
        Imgproc.dilate(processingFrames.get(0), processingFrames.get(1), basicDilateKernel);

        return processingFrames.get(1).clone(); // Clone for thread-safety
    }

    protected static ArrayList<MatOfPoint> BakedProcessing(Mat input) {
        pipe.process(input);
        return pipe.getFilterContoursOutput();
    }
}