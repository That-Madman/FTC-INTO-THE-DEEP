package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;
import org.firstinspires.ftc.teamcode.extLib.hardware.Controller;

@TeleOp
public class TestTele extends OpMode {
    private Board board;
    Controller con;

    @Override
    public void init() {
        board = new Board(hardwareMap);
        con = new Controller(gamepad1);
    }

    @Override
    public void loop() {

        board.powerArm(con.rightTrigger - con.leftTrigger);

        board.powerExtent(con.rightBumperHeld ? 1 : con.leftBumperHeld ? -1 : 0);

        telemetry.addData("extension", board.getExtentPosition());
        telemetry.addData("arm", board.getArmPosition());
        telemetry.addData("braking?", board.braking);
        telemetry.update();
        con.update();
    }
}
