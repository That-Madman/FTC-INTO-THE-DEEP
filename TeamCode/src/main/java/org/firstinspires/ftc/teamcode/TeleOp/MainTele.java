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

        //for spool function
        board.setSpoolPower(gamepad2.right_trigger - gamepad2.left_trigger);
    }
}
