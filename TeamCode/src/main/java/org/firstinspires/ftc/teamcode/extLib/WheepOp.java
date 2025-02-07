package org.firstinspires.ftc.teamcode.extLib;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.extLib.hardware.AverageDistanceSensor;
import org.firstinspires.ftc.teamcode.extLib.hardware.Board;
import org.firstinspires.ftc.teamcode.extLib.hardware.DistanceLocalizer;
import org.firstinspires.ftc.teamcode.extLib.wheelieExt.PathFollowerWrapper;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

public abstract class WheepOp extends LinearOpMode {
    protected PathFollowerWrapper followerWrapper;
    protected Board board;
    protected Pose2D startPose = new Pose2D(0,0,0);

    protected DistanceLocalizer distanceLocalizer;

    @Override
    public void runOpMode () {
        board = new Board (hardwareMap);
        followerWrapper = new PathFollowerWrapper(hardwareMap, startPose, 8);
        distanceLocalizer = new DistanceLocalizer(hardwareMap, board);

        telemetry.addLine("Initialized");
        telemetry.update();

        onInit();

        while(opModeInInit()){
            distanceLocalizer.update();
        }

        run();

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

    public abstract void onInit ();

    public abstract void run ();

    protected void followPath(Pose2D[] a) {
        //Sets the path for follower
        double pathLength = 0;
        for(int i = 0; i < a.length-1; i++)
            pathLength += Math.hypot(a[i].x-a[i+1].x, a[i].y-a[i+1].y);
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));
        ElapsedTime timer = new ElapsedTime();
        double timeForPath = pathLength/followerWrapper.SPEED_PERCENT*18.0;

        while (timer.seconds() <= timeForPath &&
                followerWrapper.getFollower() != null && opModeIsActive()) { //Runs until end of path is reached
            //behind.update();
            //right.update();
            followerWrapper.updatePose(board.getCurrentPose()); //Updates position
            double[] vectorCom = followerWrapper.follow(); //Gets the movement vector
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

                /*telemetry.addData("Movement",
                          followerWrapper.followerValues().x + ", " +
                                followerWrapper.followerValues().y + ", " +
                                followerWrapper.followerValues().h);*/
            }

            telemetry.update();
        }
    }
    protected void followPath(Pose2D[] a, Pose2D diff) {
        double pathLength = 0;
        for(int i = 0; i < a.length-1; i++)
            pathLength += Math.hypot(a[i].x-a[i+1].x, a[i].y-a[i+1].y);
        ElapsedTime timer = new ElapsedTime();
        double timeForPath = pathLength/followerWrapper.SPEED_PERCENT*18.0;

        //Sets the path for follower
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));

        double initBehind = distanceLocalizer.getBack();
        double initRight = distanceLocalizer.getRight();

        while (timer.seconds() <= timeForPath &&
                followerWrapper.getFollower() != null && opModeIsActive() &&
                (Math.abs((distanceLocalizer.getBack()-initBehind)-diff.x)<=2 ||
                Math.abs((distanceLocalizer.getRight()-initRight)-diff.y)<=2)) {
            //Runs until end of path is reached
            distanceLocalizer.update();
            followerWrapper.updatePose(board.getCurrentPose()); //Updates position
            double[] vectorCom = followerWrapper.follow(); //Gets the movement vector
            if(Math.abs((distanceLocalizer.getBack()-initBehind)-diff.x)<=2)
                vectorCom[0] = 0;
            if(Math.abs((distanceLocalizer.getRight()-initRight)-diff.y)<=2)
                vectorCom[1]=0;
            board.drive(vectorCom[0], -vectorCom[1], -vectorCom[2]); //Uses vector to power motors

            telemetry.addData("Behind", distanceLocalizer.getBack());
            telemetry.addData("Right", distanceLocalizer.getRight());
            telemetry.addData("Behind", distanceLocalizer.getBack()-initBehind);
            telemetry.addData("Right", distanceLocalizer.getRight()-initRight);

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

                /*telemetry.addData("Movement",
                          followerWrapper.followerValues().x + ", " +
                                followerWrapper.followerValues().y + ", " +
                                followerWrapper.followerValues().h);*/
            }

            telemetry.update();
        }
    }
    protected void maintain() {
        double[] vector = followerWrapper.maintainPos();
        board.drive(vector[0], -vector[1], -vector[2]);
    }

}