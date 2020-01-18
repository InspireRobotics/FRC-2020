package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

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
        public static final int DRIVE_FL = 5;
        public static final int DRIVE_FR = 6;
        public static final int DRIVE_BL = 7;
        public static final int DRIVE_BR = 8;
    }

    public static class POWER {
        public static final double TELEOP_MAX = 0.5;
        public static final double TELEOP_MIN = 0.3;
        public static final double AUTO_MAX = 0.2;
        public static final double AUTO_MIN = 0.15;
    }

    public static class ENCODER {
        public static final double COUNTS_TO_INCHES = (5.5 * Math.PI) / 10.75 / 1.1; // 6π inch
                                                                                     // wheel
                                                                                     // circumfrence
                                                                                     // with
                                                                                     // encoders set
                                                                                     // to 42 counts
                                                                                     // per
                                                                                     // rotation,
                                                                                     // geared to a
                                                                                     // 10.75:1
                                                                                     // ratio
    }

    public static class Joysticks {
        public static final XboxController drive = new XboxController(0);
        public static final XboxController aux = new XboxController(1);
    }

}