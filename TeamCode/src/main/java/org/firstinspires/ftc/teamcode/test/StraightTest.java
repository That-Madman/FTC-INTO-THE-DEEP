package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autoSystems.WheelOp;

import Wheelie.Pose2D;

@Autonomous (group = "Tests")
public class StraightTest extends WheelOp {

    Pose2D[] points = new Pose2D[] {
            new Pose2D(0,0,0),
            new Pose2D(50, 0,0),
            new Pose2D(100, 0,0)
    };

    @Override
    public void onInit() {}

    @Override
    public void run () {
        followPath(points);

        while(opModeIsActive()){
            maintain();

            followerWrapper.updatePose(board.getCurrentPose()); //Updates position
            telemetry.addLine("Path is complete");
            telemetry.addData("Position",
                    followerWrapper.getPose().x + ", " +
                            followerWrapper.getPose().y + ", " +
                            followerWrapper.getPose().h);

            telemetry.update();
        }
    }
}