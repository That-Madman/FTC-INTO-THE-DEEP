package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Autonomous.PathFollowerWrapper;

import org.firstinspires.ftc.teamcode.Board;
import org.firstinspires.ftc.teamcode.Wheelie.PathFollower;
import org.firstinspires.ftc.teamcode.Wheelie.Pose2D;

@TeleOp
public class LocalizationTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        PathFollowerWrapper drive = new PathFollowerWrapper(hardwareMap, new Pose2D(0, 0, 0), 8);
        Board board = new Board();
        board.init(hardwareMap);
        telemetry.addLine("Initialized");
        telemetry.addLine(drive.getPoseString());
        telemetry.addLine(drive.getPoseString());
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            drive.updatePose(board.getDrivePosition(1), board.getDrivePosition(2), board.getAngle());
            telemetry.addLine(drive.getPoseString());
            telemetry.addData("Horizontal wheel", drive.getHoriOdom());
            telemetry.addData("Vertical wheel", drive.getVertOdom());
            telemetry.update();
        }
    }
}