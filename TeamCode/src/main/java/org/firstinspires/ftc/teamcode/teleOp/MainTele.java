package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

@TeleOp (name = "The one you use")
public class MainTele extends OpMode {
    Board board;

    @Override
    public void init () {
        board = new Board(hardwareMap);
    }

    @Override
    public void loop () {
        board.driveFieldRelative(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x
        );
    }
}
