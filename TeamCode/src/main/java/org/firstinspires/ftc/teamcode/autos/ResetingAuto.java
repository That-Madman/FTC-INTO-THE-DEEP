package org.firstinspires.ftc.teamcode.extLib;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.extLib.wheelieExt.PathFollowerWrapper;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

public class ResetingAuto extends LinearOpMode {
    private PathFollowerWrapper followerWrapper;
    protected Board board;
    public ElapsedTime time;

    private Pose2D[] path1 = new Pose2D[] {
            new Pose2D(0,0,0),
            new Pose2D(24, 0, 0),
            new Pose2D(24, 24, 0)
    },
    path2 = new Pose2D[] {
            new Pose2D(0,0,0),
            new Pose2D(0,0, Math.toRadians(90)),
            new Pose2D(0, 0, Math.toRadians(180))
    };

    @Override
    public void runOpMode () {
        time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        board = new Board (hardwareMap);
        board.resetIMU();
        followerWrapper = new PathFollowerWrapper(hardwareMap, new Pose2D(0,0), 8);

        waitForStart();

        followPath(path1, 0);
        followPath(path2, 5);
        followPath(path1, 0);
        followPath(path2, 0);

        while(opModeIsActive()){
            telemetry.addLine("Path is complete");
            telemetry.addData("Position",
                    followerWrapper.getPose().x + ", " +
                            followerWrapper.getPose().y + ", " +
                            followerWrapper.getPose().h);

            telemetry.update();
        }
    }

    protected void followPath(Pose2D[] a, final double waitTime) {
        //Sets the path for follower
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));
        followerWrapper.getFollower().tele = telemetry; //TODO delete

        while (followerWrapper.getFollower() != null && opModeIsActive()) { //Runs until end of path is reached
            followerWrapper.updatePose(board.getAngle()); //Updates position
            double[] vectorCom = followerWrapper.follow(); //Gets the movement vector
            board.drive(vectorCom[0], vectorCom[1], vectorCom[2]); //Uses vector to power motors

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

        followerWrapper.getLocalization().resetPose();
        time.reset();
        while (time.time() < waitTime && opModeIsActive());
    }
}
