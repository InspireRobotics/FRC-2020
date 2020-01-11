package frc.robot.subsystems;

import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.Robot;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * The drivetrain on the robot.
 * 
 * This is responsible for managing the state of the drivetrain, including
 * encoder values (velocity, position, etc.)
 */
public class Camera extends SubsystemBase {

    private UsbCamera camera;
    private CvSink targetInput;
    private CvSource targetOutput;
    private MjpegServer driverFeed;


    @Override
    public void init(Robot robot) {
        camera = new UsbCamera("Front Camera", 0); // Set up the input stream for the USB camera

        targetInput = new CvSink("Vision Target Processing"); // Set up the processing segment of the vision targeting pipeline
        targetInput.setSource(camera); // Connect the camera to the processing segment

        targetOutput = new CvSource("Vision Target Output", VideoMode.PixelFormat.kMJPEG, 640, 480, 30);

        driverFeed = new MjpegServer("Baked Output", 1181);
        driverFeed.setSource(targetOutput);
    }

    @Override
    public void periodic() {
        Mat input = new Mat();
        Mat output = new Mat();
        targetInput.grabFrame(input);

        Imgproc.bilateralFilter(input, output, 2, 1, 4);

        targetOutput.putFrame(input);
    }

    @Override
    public void disable() {
        /* As there are no moving parts, and there is not a great way to re-enable, this does nothing */
    }

    @Override
    String getSubsystemName() {
        return "Camera";
    }
}