package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.Board;
import org.firstinspires.ftc.teamcode.extLib.wheelieExt.PathFollowerWrapper;

import Wheelie.Pose2D;


@TeleOp(group = "Test")
public class RPMTest extends LinearOpMode {
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

        int[] startTicks = new int[]{
                board.getDrivePosition(0),
                board.getDrivePosition(1),
                board.getDrivePosition(2),
                board.getDrivePosition(3)
        };
        long millis = System.currentTimeMillis()/1000;

        while (opModeIsActive()) {
            SparkFunOTOS.Pose2D currentPose = board.getCurrentPose();

            telemetry.addLine("Current Pose from SparkFun OTO:");
            telemetry.addData("X Position", currentPose.x);
            telemetry.addData("Y Position", currentPose.y);
            telemetry.addData("Heading", currentPose.h);
            board.drive(-gamepad1.left_stick_y*.5, 0, 0);

            if(gamepad1.left_stick_y != 0){
                try {
                    telemetry.addLine("Ticks per second");
                    for (int i = 0; i < 4; i++) {
                        /*telemetry.addData(String.valueOf(i), (board.getDrivePosition(i) - startTicks[i])
                                / (System.currentTimeMillis() / 1000 - millis));*/
                        telemetry.addData(String.valueOf(i), board.getVelocity(i));
                    }
                } catch(Exception e){

                }
            }else{
                 startTicks = new int[]{
                        board.getDrivePosition(0),
                        board.getDrivePosition(1),
                        board.getDrivePosition(2),
                        board.getDrivePosition(3)
                };
                 millis = System.currentTimeMillis() / 1000;
            }

            telemetry.update();
        }
    }
}

//91
//93
//91
//avg 91.6