package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.Constants;
import frc.robot.Robot;

public class SpinTheWheel extends SubsystemBase {

    private DoubleSolenoid lift;
    private Spark spin;

    // TODO: Add color sensor!

    //private ColorSensorV3 colorSensorV3;
    private final ColorMatch match = new ColorMatch();

    private boolean lifted = false;
    private double liftTarget = 0;
    private Constants.COLORS color = null;

    /**
     * Initializes the motors for SpinTheWheel
     *
     * @param robot
     *            The robot instance
     */
    @Override
    void init(Robot robot) {
        match.addColorMatch(Constants.COLOR_SENSOR.kBlueTarget);
        match.addColorMatch(Constants.COLOR_SENSOR.kGreenTarget);
        match.addColorMatch(Constants.COLOR_SENSOR.kRedTarget);
        match.addColorMatch(Constants.COLOR_SENSOR.kYellowTarget);

        lift = new DoubleSolenoid(1, Constants.PNEUMATICS.FLIP_SPIN_01,
                Constants.PNEUMATICS.FLIP_SPIN_02);
        spin = new Spark(Constants.CAN.SPW_SPIN);

        //colorSensorV3 = new ColorSensorV3(Constants.COLOR_SENSOR.colorPort);
    }

    /**
     * Disables the mechanism
     */
    @Override
    public void disable() {
//        lift.close();
        spin.set(0);
    }

    /**
     * Resets the power output and queries the stage 3 color.
     */
    @Override
    public void periodic() {
        spin.set(0);

        if (color == null) {
            String rawColor = DriverStation.getInstance().getGameSpecificMessage();
            if (rawColor.length() > 0) {

                // NOTE: Our target color should be a quarter turn off of the actual color to
                // match. Also note that
                // due to the arrangement of colors spin direction is ambiguous.

                switch (rawColor.charAt(0)) {
                case 'R':
                    color = Constants.COLORS.BLUE;
                    break;
                case 'Y':
                    color = Constants.COLORS.GREEN;
                    break;
                case 'G':
                    color = Constants.COLORS.YELLOW;
                    break;
                case 'B':
                    color = Constants.COLORS.RED;
                    break;
                default:
                    System.out.println("GOT CORRUPT DATA");
                    break;
                }
            }
        }
    }

    /**
     * Signals the mechanism to raise and start spinning.
     */
    public void raise() {
        if (!lifted) {
            lift.set(DoubleSolenoid.Value.kReverse);
            lifted = true;
        }
    }

    /**
     * Signals the mechanism to stop spinning and to lower.
     */
    public void lower() {
        if (lifted) {
            lift.set(DoubleSolenoid.Value.kForward);
            lifted = false;
        }
    }

    public void toggle() {
        if (!lifted) {
            raise();
        } else {
            lower();
        }
    }

    /**
     * Lifts the spinner, spins it, and lowers it as needed.
     */
    public void run() {
        double bias = lifted ? 0.2 : -0.2;

        if (color != null) {
            if (getColor() != color) {
                spin.set(1);
            }
        } else {
            if (lifted) {
                spin.set(1);
            }
        }
    }

    /**
     * Gets the currently detected color
     *
     * @return Returns a color from the Constants.COLORS enum.
     */
    public Constants.COLORS getColor() {
        /*Color match = this.match.matchClosestColor(colorSensorV3.getColor()).color;

        if (match == Constants.COLOR_SENSOR.kBlueTarget) {
            return Constants.COLORS.BLUE;
        } else if (match == Constants.COLOR_SENSOR.kGreenTarget) {
            return Constants.COLORS.GREEN;
        } else if (match == Constants.COLOR_SENSOR.kRedTarget) {
            return Constants.COLORS.RED;
        } else if (match == Constants.COLOR_SENSOR.kYellowTarget) {
            return Constants.COLORS.YELLOW;
        }*/

        return null;
    }

    @Override
    String getSubsystemName() {
        return null;
    }
}
