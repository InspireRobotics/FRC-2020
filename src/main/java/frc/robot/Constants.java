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

        public static final int SHOOT_L = 3;
        public static final int SHOOT_R = 4;

        public static final int INTAKE = 0; // TODO: Add final values for hopper motors
        public static final int HOPPER_HOLD = 0;
        public static final int HOPPER_LIFT = 0;
        public static final int HOPPER_SPIN_BOTTOM = 0;
        public static final int HOPPER_SPIN_TOP = 0;

        public static final int SPW_LIFT = 0;
        public static final int SPW_SPIN = 0;
    }

    public static class DIO {
        public static final int PHOTOGATE = 0;
    }

    public static class POWER {
        public static final double TELEOP_MAX = 0.5;
        public static final double TELEOP_MIN = 0.3;
        public static final double AUTO_MAX = 0.3;
        public static final double AUTO_MIN = 0.15;
    }

    public static class ENCODER {
        public static final double COUNTS_TO_INCHES_DRIVETRAIN = (5.5 * Math.PI) / 10.75 / 1.1;
        public static final double MAX_RPM = 3500; // Maximum RPM used in PID-controlled
                                                   // applications
        public static final double COUNTS_TO_ROTATIONS_STWLIFT = 1 / 42 / 10; // 42 counts per rot, 10:1 gearbox
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
    }

    public enum COLORS {
        RED,
        YELLOW,
        GREEN,
        BLUE
    }
}