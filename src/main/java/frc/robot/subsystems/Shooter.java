package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants;
import frc.robot.Robot;

public class Shooter extends SubsystemBase {

    CANSparkMax leftWheel;
    CANSparkMax rightWheel;

    @Override
    void init(Robot robot) {
        leftWheel = new CANSparkMax(Constants.CAN.SHOOT_L,
                CANSparkMaxLowLevel.MotorType.kBrushless);
        rightWheel = new CANSparkMax(Constants.CAN.SHOOT_R,
                CANSparkMaxLowLevel.MotorType.kBrushless);

        rightWheel.setInverted(true);
    }

    @Override
    void disable() {
        leftWheel.set(0);
        rightWheel.set(0);
    }

    @Override
    public void periodic() {
        leftWheel.set(0);
        rightWheel.set(0);

        SmartDashboard.putNumber("ShooterLeft", leftWheel.getEncoder().getVelocity());
        SmartDashboard.putNumber("ShooterRight", rightWheel.getEncoder().getVelocity());
    }

    public void setPower(double power) {
        leftWheel.set(power);
        rightWheel.set(power);
    }

    @Override
    String getSubsystemName() {
        return "Shooter";
    }
}
