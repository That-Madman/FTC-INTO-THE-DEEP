package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

@TeleOp
public class MainTele extends OpMode {
    private Board board;

    @Override
    public void init() {
        board = new Board(hardwareMap);
    }

    @Override
    public void loop() {
        board.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        board.powerArm(gamepad1.left_trigger - gamepad1.right_trigger);
        board.moveArm(gamepad1.dpad_up ? 1 : gamepad1.dpad_down ? -1 : 0);
    }
}
