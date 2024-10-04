package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Autonomous.PathFollowerWrapper;

import Wheelie.PathFollower;
import Wheelie.Pose2D;

@TeleOp
public class LocalizationTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        PathFollowerWrapper drive = new PathFollowerWrapper(hardwareMap, new Pose2D(0, 0, 0), 8);
        telemetry.addLine("Initialized");
        telemetry.addLine(drive.getPoseString());
        telemetry.addLine(drive.getPoseString());
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            drive.followPath(1,1);//TODO doesn't work, add board

            telemetry.addLine(drive.getPoseString());
            telemetry.addData("Horizontal wheel", drive.getHoriOdom());
            telemetry.addData("Vertical wheel", drive.getVertOdom());
            telemetry.update();
        }
    }
}