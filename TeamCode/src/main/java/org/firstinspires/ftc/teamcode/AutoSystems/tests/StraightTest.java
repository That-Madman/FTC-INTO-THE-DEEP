package org.firstinspires.ftc.teamcode.AutoSystems.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.teamcode.AutoSystems.PathFollowerWrapper;
import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

@Autonomous
public class StraightTest extends LinearOpMode {
    protected PathFollowerWrapper followerWrapper;
    protected Board board;

    Pose2D[] points = new Pose2D[] {
            new Pose2D(0,0,0),
            new Pose2D(30, 0,0),
            new Pose2D(102, 0,0)
    };

    @Override
    public void runOpMode () {
        board = new Board (hardwareMap);
        followerWrapper = new PathFollowerWrapper(hardwareMap, new Pose2D(0,0), 8);

        waitForStart();

        followPath(points);

        while(opModeIsActive()){
            followerWrapper.updatePose(board.getCurrentPose()); //Updates position
            telemetry.addLine("Path is complete");
            telemetry.addData("Position",
                    followerWrapper.getPose().x + ", " +
                            followerWrapper.getPose().y + ", " +
                            followerWrapper.getPose().h);

            telemetry.update();
        }
    }

    protected void followPath(Pose2D[] a) {
        //Sets the path for follower
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));

        while (followerWrapper.getFollower() != null && opModeIsActive()) { //Runs until end of path is reached
            Pose2D pos = board.getCurrentPose();
            followerWrapper.updatePose(pos); //Updates position
            double[] vectorCom = followerWrapper.followPath(); //Gets the movement vector
            board.drive(vectorCom[0], -vectorCom[1], -vectorCom[2]); //Uses vector to power motors

            telemetry.addData("Position",
                    followerWrapper.getPose().x + ", " +
                            followerWrapper.getPose().y + ", " +
                            followerWrapper.getPose().h);
            telemetry.addData("Vector", Arrays.toString(vectorCom));

            telemetry.addLine();
            if (followerWrapper.getFollower() != null) {
                Pose2D goal = a[followerWrapper.getCurrentWayPoint() + 1];
                telemetry.addData("Goal",
                        goal.x + ", " +
                                goal.y + ", " +
                                goal.h);

                telemetry.addData("Movement",
                        followerWrapper.followerValues().x + ", " +
                                followerWrapper.followerValues().y + ", " +
                                followerWrapper.followerValues().h);
            }

            telemetry.update();
        }
    }
}