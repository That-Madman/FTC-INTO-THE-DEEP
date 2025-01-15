package org.firstinspires.ftc.teamcode.extLib.autos.systems.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.extLib.autos.systems.PathFollowerWrapper;
import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

import Wheelie.Pose2D;


@TeleOp(group = "Test")
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
            Pose2D currentPose = board.getCurrentPose();

            telemetry.addLine("Current Pose from SparkFun OTO:");
            telemetry.addData("X Position", currentPose.x);
            telemetry.addData("Y Position", currentPose.y);
            telemetry.addData("Heading", currentPose.h);

            telemetry.update();
        }
    }
}