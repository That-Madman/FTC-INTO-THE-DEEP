package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.Board;
import org.firstinspires.ftc.teamcode.extLib.wheelieExt.PathFollowerWrapper;

import Wheelie.Pose2D;


@TeleOp(group = "Test")
public class LocalizationTest extends LinearOpMode {
    Board board = null;

    @Override
    public void runOpMode() throws InterruptedException {
        PathFollowerWrapper drive = new PathFollowerWrapper(hardwareMap, new Pose2D(0, 0, 0), 8);
        board = new Board(hardwareMap);

        telemetry.addLine("Initialized");
        /*telemetry.addLine(drive.getPoseString());
        telemetry.addLine(drive.getPoseString());
        telemetry.update();*/

        waitForStart();
        board.resetIMU();

        while (opModeIsActive()) {
            drive.updatePose(board.getAngle());
            Pose2D currentPose = drive.getPose();

            telemetry.addLine("Current Pose from SparkFun OTO:");
            telemetry.addData("X Position", currentPose.x);
            telemetry.addData("Y Position", currentPose.y);
            telemetry.addData("Heading", currentPose.h);

            telemetry.update();
        }
    }
}