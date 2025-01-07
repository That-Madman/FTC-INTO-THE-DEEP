package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

@TeleOp
public class MainTele extends OpMode {
    private Board board;

    private boolean driveRel;

    private byte posResetTimes;

    // Button holding trackers
    private boolean a1Held;
    private boolean y1Held;

    public void init () {
        board = new Board(hardwareMap);
    }

    public void loop () {
        if (gamepad1.a && !a1Held) {
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

        if (gamepad1.y && !y1Held) {
            board.resetImu();
            ++posResetTimes;
        }

        a1Held = gamepad1.a;
        y1Held = gamepad1.y;

        // DEBUG TELEMETRY
        telemetry.addData("Driving", (driveRel) ? "Robot Relative" : "Field Relative");
        telemetry.addData("IMU Reset times", posResetTimes);
    }
}
