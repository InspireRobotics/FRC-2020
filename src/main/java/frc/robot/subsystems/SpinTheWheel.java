package frc.robot.subsystems;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.Constants;
import frc.robot.Robot;

public class SpinTheWheel extends SubsystemBase {

    private Spark lift;
    private Spark spin;

    //TODO: Add color sensor!

    private boolean lifted = false;
    private double liftTarget = 0;
    private Constants.COLORS color = null;

    @Override
    void init(Robot robot) {
        lift = new Spark(Constants.CAN.SPW_LIFT);
        spin = new Spark(Constants.CAN.SPW_SPIN);
    }

    @Override
    void disable() {
        lift.set(0);
        spin.set(0);
    }

    @Override
    public void periodic() {
        lift.set(0);
        spin.set(0);

        String rawColor = DriverStation.getInstance().getGameSpecificMessage();
        if (rawColor.length() > 0) {
            switch(rawColor.charAt(0)) {
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

    public void raise() {
        if (!lifted) {
            liftTarget = lift.getPosition() * Constants.ENCODER.COUNTS_TO_ROTATIONS_STWLIFT + 1.0/4;
            lifted = true;
        }
    }

    public void lower() {
        if (lifted) {
            liftTarget = lift.getPosition() * Constants.ENCODER.COUNTS_TO_ROTATIONS_STWLIFT - 1.0/4;
        }
    }

    public void run() {
        double bias = lifted ? 0.2 : -0.2;
        lift.set(Math.atan(lift.getPosition() - liftTarget) * 2 / Math.PI + bias);

        if (color != null) {
            if (false /*COLOR SENSOR CODE HERE*/) {
                spin.set(1);
            }
        } else {
            if (lifted && Math.abs(lift.getPosition() - liftTarget) < 0.05) {
                spin.set(1);
            }
        }
    }

    /**
     *
     * @return Returns a color from 1-4
     */
    public Constants.COLORS getColor() {
        //TODO: Implement color sensor
        return null;
    }

    @Override
    String getSubsystemName() {
        return null;
    }
}
