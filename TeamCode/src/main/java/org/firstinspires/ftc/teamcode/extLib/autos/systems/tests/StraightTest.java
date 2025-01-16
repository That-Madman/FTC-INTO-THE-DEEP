package org.firstinspires.ftc.teamcode.extLib.autos.systems.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.extLib.autos.systems.PathFollowerWrapper;
import org.firstinspires.ftc.teamcode.extLib.autos.systems.WheelOp;
import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

@Autonomous
public class StraightTest extends WheelOp {

    Pose2D[] points = new Pose2D[] {
            new Pose2D(0,0,0),
            new Pose2D(50, 0,0),
            new Pose2D(100, 0,0)
    };

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