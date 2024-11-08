package org.firstinspires.ftc.teamcode.extLib;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.WheelieThings.PathFollowerWrapper;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

public abstract class WheelOp extends LinearOpMode {
    private PathFollowerWrapper followerWrapper = null;
    protected Board board = null;
    ElapsedTime time = null;

    @Override
    public void runOpMode () {
        time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        board = new Board (hardwareMap);
        followerWrapper = new PathFollowerWrapper(hardwareMap, new Pose2D(0,0), 8);

        waitForStart();

        run();
    }

    public abstract void run ();

    protected void followPath(Pose2D[] a, final double waitTime) {
        //Sets the path for follower
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));

        while (followerWrapper.getFollower() != null && opModeIsActive()) { //Runs until end of path is reached
            followerWrapper.updatePose(board.getAngle()); //Updates position
            double[] vectorCom = followerWrapper.followPath(); //Gets the movement vector
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
            }

            telemetry.update();
        }

        time.reset();
        while (time.time() < waitTime && opModeIsActive());
    }
}
