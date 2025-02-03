package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.extLib.autoSystems.PathFollowerWrapper;
import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

import Wheelie.Pose2D;


@TeleOp(group = "Tests")
public class LocalizationTest extends LinearOpMode {
    Board board = null;

    @Override
    public void runOpMode() throws InterruptedException {
        PathFollowerWrapper drive = new PathFollowerWrapper(hardwareMap, new Pose2D(0, 0, 0), 8);
        board = new Board(hardwareMap);

        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            Pose2D current = board.getCurrentPose();

            telemetry.addLine("Current Pose from SparkFun OTO:");
            telemetry.addData("X Position", current.x);
            telemetry.addData("Y Position", current.y);
            telemetry.addData("Heading", current.h);

            telemetry.update();
        }
    }
}