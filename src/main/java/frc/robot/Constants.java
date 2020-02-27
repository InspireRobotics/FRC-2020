package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;

/**
 * The constants for the robot such as CAN ports, PWM ports, joysticks, PID
 * constants, etc.
 * 
 * There are different groups of constants that are responsible for different
 * things.
 */
public class Constants {

    public static class PID {
        public static final double kP_EXAMPLE = 0.0;
        public static final double kI_EXAMPLE = 0.0;
        public static final double kD_EXAMPLE = 0.0;
    }

    public static class CAN {
        public static final int DRIVE_FL = 6;
        public static final int DRIVE_FR = 3;
        public static final int DRIVE_BL = 7;
        public static final int DRIVE_BR = 9;

        public static final int SHOOT_L = 13;
        public static final int SHOOT_R = 14;

        public static final int HOPPER_HOLD = 15;
        public static final int HOPPER_LIFT = 16;
        public static final int HOPPER_SPIN_BOTTOM = 5;
        public static final int HOPPER_SPIN_TOP = 8;

        public static final int SPW_SPIN = 17;
    }

    public static class DIO {
        public static final int PHOTOGATE = 9;
    }

    public static class POWER {
        public static final double TELEOP_MAX = 0.4;
        public static final double TELEOP_MIN = 0.25;
        public static final double AUTO_MAX = 0.3;
        public static final double AUTO_MIN = 0.15;
    }

    public static class ENCODER {
        public static final double COUNTS_TO_INCHES_DRIVETRAIN = (5.5 * Math.PI) / 10.75 / 1.1;
        public static final double MAX_RPM = 3500; // Maximum RPM used in PID-controlled
                                                   // applications
        public static final double COUNTS_TO_ROTATIONS_STWLIFT = 1 / 42 / 10; // 42 counts per rot,
                                                                              // 10:1 gearbox
    }

    public static class Joysticks {
        public static final XboxController drive = new XboxController(0);
        public static final XboxController aux = new XboxController(1);
    }

    public static class I2C {
        public static final int PIXYCAM = 0x52;
    }

    public static class PIXYCAM {
        public static final int NO_CHECKSUM_SYNC = 0xc1ae;
        public static final int CHECKSUM_SYNC = 0xc1af;

        public static final double CENTER_X = 315.0 / 2.0;
    }

     public static class PNEUMATICS {
     public static final int FLIP_SPIN_01 = 5;
     public static final int FLIP_SPIN_02 = 0;
     public static final int FLIP_INTAKE_01 = 1;
     public static final int FLIP_INTAKE_02 = 6;
     }

    public static class COLOR_SENSOR {
        public static final edu.wpi.first.wpilibj.I2C.Port colorPort = edu.wpi.first.wpilibj.I2C.Port.kOnboard;

        public static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
        public static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
        public static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
        public static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    }

    public enum COLORS {
        RED, YELLOW, GREEN, BLUE
    }
}