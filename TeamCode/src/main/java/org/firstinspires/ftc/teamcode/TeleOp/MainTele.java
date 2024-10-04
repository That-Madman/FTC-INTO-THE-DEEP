package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Board;

@TeleOp
public class MainTele extends OpMode {
    boolean bHeld = false;
    boolean clawOpen = false;

    //aAlreadyHeld = false;

    Board board = new Board();

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
        board.setSpoolPower(gamepad2.right_trigger - gamepad2.left_trigger);

        if (gamepad2.b && !bHeld) {
            clawOpen = !clawOpen;
        }

        board.setClaw(clawOpen);

        bHeld = gamepad2.b;

        /* Code here is place holder code for the horizontal expansion system
        if (gamepad2.a && !aAlreadyHeld) {
            extended = !extended
           }
        aAlreadyHeld = (gamepad2.a);
         */

    }
}
