package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Board;

@TeleOp
public class MainTele extends OpMode {
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

        if (gamepad1.right_trigger > 0) {
           board.setSpoolPower(1);
        }
        else if (gamepad1.left_trigger > 0) {
            board.setSpoolPower(-1);
            }
        else {
            board.setSpoolPower(0);
         }
        //board.setSpoolPower(gamepad.right_trigger - gamepad.left_trigger);
    }
}
