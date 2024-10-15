package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.SpoolStates.BUCKETHIGH;
import static org.firstinspires.ftc.teamcode.SpoolStates.BUCKETLOW;
import static org.firstinspires.ftc.teamcode.SpoolStates.CHAMBERHIGH;
import static org.firstinspires.ftc.teamcode.SpoolStates.CHAMBERLOW;
import static org.firstinspires.ftc.teamcode.SpoolStates.MAXIMUM;
import static org.firstinspires.ftc.teamcode.SpoolStates.MINIMUM;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Board;

@TeleOp
public class MainTele extends OpMode {
    boolean aHeld = false;
    boolean bHeld = false;
    boolean yHeld = false;
    boolean clawOpen = false;
    boolean shortHorz = false;
    boolean rightBumperHeld = false;
    boolean leftBumperHeld = false;

    int spoolTarg = 0;
    int sweepState = 0;

    Board board = new Board();

    /**
     * The dilation of the spool
     */
    final int spoolDil = 100;
    final double slowDil = 0.25;

    @Override
    public void init() {
        board.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.left_trigger > 0) {
            board.driveFieldRelative(
                    -gamepad1.left_stick_y * slowDil,
                    gamepad1.left_stick_x * slowDil,
                    gamepad1.right_stick_x * slowDil
            );
        } else {
            board.driveFieldRelative(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x
            );
        }

        /*
        //for spool function
        spoolTarg += (int) (spoolDil * (gamepad2.right_trigger - gamepad2.left_trigger));
       //spoolTarg = min(spoolTarg, MAXIMUM);
        spoolTarg = max(spoolTarg, MINIMUM);
*/
        if (gamepad2.right_bumper && !rightBumperHeld) {
            switch(spoolTarg) {
                case MINIMUM:
                    spoolTarg = CHAMBERLOW;
                    break;
                case CHAMBERLOW:
                    spoolTarg = BUCKETLOW;
                    break;
                case CHAMBERHIGH:
                    spoolTarg = BUCKETHIGH;
                    break;
                case BUCKETLOW:
                    spoolTarg = CHAMBERHIGH;
                    break;
                case BUCKETHIGH:
                    spoolTarg = MAXIMUM;
                    break;
                default:
                    spoolTarg = MINIMUM;
                    break;
            }
        } else if (gamepad2.left_bumper && !leftBumperHeld) {
            switch (spoolTarg) {
                case CHAMBERHIGH:
                    spoolTarg = BUCKETLOW;
                    break;
                case BUCKETLOW:
                    spoolTarg = CHAMBERLOW;
                    break;
                case BUCKETHIGH:
                    spoolTarg = CHAMBERHIGH;
                    break;
                case MAXIMUM:
                    spoolTarg = BUCKETHIGH;
                    break;
                default:
                    spoolTarg = MINIMUM;
                    break;
            }
        }
        rightBumperHeld = gamepad2.right_bumper;
        leftBumperHeld = gamepad2.left_bumper;

        //board.setSpoolPos(spoolTarg);
        board.setSpoolPower(gamepad2.right_trigger - gamepad2.left_trigger);

        if (gamepad2.b && !bHeld) {
            clawOpen = !clawOpen;
        }

        board.setClaw(clawOpen);

        bHeld = gamepad2.b;

        if (gamepad2.a && !aHeld) {
           shortHorz = !shortHorz;
        }

        board.setHorzExt(shortHorz);

        aHeld = gamepad2.a;

        if (gamepad2.y && !yHeld) {
            ++sweepState;
            sweepState %= 3;
        }

        switch (sweepState) {
            case 0:
                board.setSweep(0);
                break;
            case 1:
                board.setSweep(1);
                break;
            case 2:
                board.setSweep(-1);
                break;
        }

        yHeld = gamepad2.y;
    }
}
