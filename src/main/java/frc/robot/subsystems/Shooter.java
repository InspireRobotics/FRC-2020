package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Shooter extends SubsystemBase {

    boolean enabled;
    ArrayList<Double> speedsLeft;
    ArrayList<Double> speedsRight;

    CANSparkMax leftWheel;
    CANSparkMax rightWheel;

    @Override
    void init(Robot robot) {
        leftWheel = new CANSparkMax(Constants.CAN.SHOOT_L, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightWheel = new CANSparkMax(Constants.CAN.SHOOT_R, CANSparkMaxLowLevel.MotorType.kBrushless);

        rightWheel.setInverted(true);
    }

    @Override
    void disable() {
        leftWheel.set(0);
        rightWheel.set(0);

        enabled = false;
        AtomicReference<String> leftOut = new AtomicReference<>("Left Samples: ");
        AtomicReference<String> rightOut = new AtomicReference<>("Right Samples: ");
        speedsLeft.forEach(speed -> leftOut.set(speedsLeft.indexOf(speed) + ": " + leftOut + speed.toString() + ", "));
        speedsRight.forEach(speed -> rightOut.set(speedsRight.indexOf(speed) + ": " + rightOut + speed.toString() + ", "));
        System.out.println(leftOut.get());
        System.out.println(rightOut.get());
    }

    @Override
    public void periodic() {
        leftWheel.set(0);
        rightWheel.set(0);

        SmartDashboard.putNumber("ShooterLeft", leftWheel.getEncoder().getVelocity());
        SmartDashboard.putNumber("ShooterRight", rightWheel.getEncoder().getVelocity());
        if (enabled) {
            speedsLeft.add(leftWheel.getEncoder().getVelocity());
            speedsRight.add(rightWheel.getEncoder().getVelocity());
        }
    }

    public void setPower(double power) {
        leftWheel.set(power);
        rightWheel.set(power);
    }

    @Override
    String getSubsystemName() {
        return "Shooter";
    }

    public void enableTeleop() {
        enabled = true;
    }
}
