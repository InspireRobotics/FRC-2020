package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Spark;

import frc.robot.Constants;
import frc.robot.Robot;

public class SpinTheWheel extends SubsystemBase {

    private Spark lift;
    private Spark spin;

    // TODO: Add color sensor!

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
        lift = new Spark(Constants.CAN.SPW_LIFT);
        spin = new Spark(Constants.CAN.SPW_SPIN);
    }

    /**
     * Disables the mechanism
     */
    @Override
    void disable() {
        lift.set(0);
        spin.set(0);
    }

    /**
     * Resets the power output and queries the stage 3 color.
     */
    @Override
    public void periodic() {
        lift.set(0);
        spin.set(0);

        if (color == null) {
            String rawColor = DriverStation.getInstance().getGameSpecificMessage();
            if (rawColor.length() > 0) {
                switch (rawColor.charAt(0)) {
                case 'R':
                    color = Constants.COLORS.RED;
                    break;
                case 'Y':
                    color = Constants.COLORS.YELLOW;
                    break;
                case 'G':
                    color = Constants.COLORS.GREEN;
                    break;
                case 'B':
                    color = Constants.COLORS.BLUE;
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
            liftTarget = lift.getPosition() * Constants.ENCODER.COUNTS_TO_ROTATIONS_STWLIFT
                    + 1.0 / 4;
            lifted = true;
        }
    }

    /**
     * Signals the mechanism to stop spinning and to lower.
     */
    public void lower() {
        if (lifted) {
            liftTarget = lift.getPosition() * Constants.ENCODER.COUNTS_TO_ROTATIONS_STWLIFT
                    - 1.0 / 4;
        }
    }

    /**
     * Lifts the spinner, spins it, and lowers it as needed.
     */
    public void run() {
        double bias = lifted ? 0.2 : -0.2;
        lift.set(Math.atan(lift.getPosition() - liftTarget) * 2 / Math.PI + bias);

        if (color != null) {
            if (false /* COLOR SENSOR CODE HERE */) {
                spin.set(1);
            }
        } else {
            if (lifted && Math.abs(lift.getPosition() - liftTarget) < 0.05) {
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
        // TODO: Implement color sensor
        return null;
    }

    @Override
    String getSubsystemName() {
        return null;
    }
}
