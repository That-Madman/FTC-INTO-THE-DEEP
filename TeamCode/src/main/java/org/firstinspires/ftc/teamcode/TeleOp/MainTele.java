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
    boolean bHeld = false;
    boolean clawOpen = false;
    boolean rightBumperHeld = false;
    boolean leftBumperHeld = false;

    int spoolTarg = 0;

    //aAlreadyHeld = false;

    Board board = new Board();

    /**
     * The dilation of the spool
     */
    final int spoolDil = 100;

    @Override
    public void init() {
        board.init(hardwareMap);
    }

    @Override
    public void loop() {
        board.driveFieldRelative(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x
        );

        //for spool function
        spoolTarg += (int) (spoolDil * (gamepad2.right_trigger - gamepad2.left_trigger));
        spoolTarg = min(spoolTarg, MAXIMUM);
        spoolTarg = max(spoolTarg, MINIMUM);
        board.setSpoolPos(spoolTarg);

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
        }
        else if (gamepad2.left_bumper && !leftBumperHeld) {
            switch (spoolTarg) {
                case CHAMBERLOW:
                    spoolTarg = MINIMUM;
                    break;
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

        board.setSpoolPos(spoolTarg);

        if (gamepad2.b && !bHeld) {
            clawOpen = !clawOpen;
        }

        board.setClaw(clawOpen);

        bHeld = gamepad2.b;

        /* Code here is place holder code for the horizontal expansion system
        if (gamepad2.a && !aAlreadyHeld) {
            extended = !extended
           }
        aAlreadyHeld = gamepad2.a;
         */

        /* spool code

         */
    }
}
