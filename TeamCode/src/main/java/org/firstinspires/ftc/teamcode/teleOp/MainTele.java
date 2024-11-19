package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

@TeleOp(name = "The one you use")
public class MainTele extends OpMode {
    Board board;

    boolean bHeld;
    boolean driveFieldRel = true;
    boolean resetImu;
    boolean yHeld;

    @Override
    public void init() {
        board = new Board(hardwareMap);
    }

    @Override
    public void init_loop() {
        if (gamepad1.y && !yHeld) {
            resetImu ^= true; // Inverts variable. Why do it like this? Because it's fun.
        }

        telemetry.clear();
        telemetry.addLine("The IMU will be " + (resetImu ? "reset" : "unchanged") + ".");
        telemetry.update();

        yHeld = gamepad1.y;
    }

    @Override
    public void start() {
        if (resetImu) {
            board.resetImu();
            resetImu = false;
        }
    }

    @Override
    public void loop() {
        if (driveFieldRel) {
            board.driveFieldRelative(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x
            );
            telemetry.addData("Driving", "Field Relative");
        } else {
            board.drive(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x
            );
            telemetry.addData("Driving", "Robot Relative");
        }

        if (gamepad1.b && !bHeld) {
            driveFieldRel ^= true;
        }

        if (gamepad1.y && !yHeld) {
            board.resetImu();
            resetImu = true;
        }

        if (resetImu) {
            telemetry.addLine("IMU has been reset.");
        }

        bHeld = gamepad1.b;
        yHeld = gamepad1.y;
    }
}
