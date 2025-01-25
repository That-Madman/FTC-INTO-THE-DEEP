package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;
import org.firstinspires.ftc.teamcode.extLib.hardware.Controller;

@TeleOp
public class MainTele extends OpMode {
    private Board board;

    private Controller con1, con2;

    private boolean driveRel;

    private byte posResetTimes;

    // Button holding trackers
    private boolean a1Held;
    private boolean y1Held;

    public void init () {
        board = new Board(hardwareMap);
        con1 = new Controller(gamepad1);
        con2 = new Controller(gamepad2);
    }

    public void loop () {
        if (con1.aPressed) {
            driveRel ^= true;
        }

        if (driveRel) {
            board.drive(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    (gamepad1.left_bumper) ? 0.5 : 1
            );
        } else {
            board.driveFieldRelative(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    (gamepad1.left_bumper) ? 0.5 : 1
            );
        }

        if(con1.aPressed)
            board.setExtentTarget(Board.defaultExt);
        else if(con1.bPressed)
            board.setExtentTarget(Board.subExt);
        else if(con1.yPressed)
            board.setExtentTarget(Board.netExt);

        board.powerArm(con2.rightTrigger - con2.leftTrigger);
        if(con2.aPressed)
            board.flipWrist();
        if(con2.bPressed)
            board.setClawPosition(board.getClawPosition() == 1 ? 0 : 1);

        if (con2.yPressed) {
            board.resetImu();
            ++posResetTimes;
        }

        con2.update();
        con1.update();

        // DEBUG TELEMETRY
        telemetry.addData("Driving", (driveRel) ? "Robot Relative" : "Field Relative");
        telemetry.addData("IMU Reset times", posResetTimes);
        telemetry.addData("Isf", board.getTarget());
        telemetry.update();
    }
}
