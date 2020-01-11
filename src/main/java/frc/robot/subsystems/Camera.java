package frc.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.*;

import frc.robot.Robot;

/**
 * The USB Camera on the shooter. This class is also responsible for setting up
 * the vision targetting proccessing.
 */
public class Camera extends SubsystemBase {

    private UsbCamera camera;
    private CvSink targetInput;
    private CvSource targetOutput;
    private MjpegServer driverFeed;

    @Override
    public void init(Robot robot) {
        camera = new UsbCamera("Front Camera", 0); // Set up the input stream for the USB camera

        targetInput = new CvSink("Vision Target Processing"); // Set up the processing segment of
                                                              // the vision targeting pipeline
        targetInput.setSource(camera); // Connect the camera to the processing segment

        targetOutput = new CvSource("Vision Target Output", VideoMode.PixelFormat.kMJPEG, 640, 480,
                30);

        driverFeed = new MjpegServer("Baked Output", 1181);
        driverFeed.setSource(targetOutput);
    }

    @Override
    public void periodic() {
        if(camera == null){ //This is true when the subsystem is not attached
            return;
        }

        Mat input = new Mat();
        Mat output = new Mat();
        targetInput.grabFrame(input);

        Imgproc.bilateralFilter(input, output, 2, 1, 4);

        targetOutput.putFrame(input);
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