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
        public static final int DRIVE_FL = 0;
        public static final int DRIVE_FR = 0;
        public static final int DRIVE_BL = 0;
        public static final int DRIVE_BR = 0;
    }

    public static class Joysticks {
        public static final XboxController drive = new XboxController(0);
        public static final XboxController aux = new XboxController(1);
    }

}