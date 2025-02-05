package org.firstinspires.ftc.teamcode.extLib;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.extLib.hardware.AverageDistanceSensor;
import org.firstinspires.ftc.teamcode.extLib.hardware.Board;
import org.firstinspires.ftc.teamcode.extLib.wheelieExt.PathFollowerWrapper;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

public abstract class WheepOp extends LinearOpMode {
    protected PathFollowerWrapper followerWrapper;
    protected Board board;
    protected Pose2D startPose = new Pose2D(0,0,0);

    protected AverageDistanceSensor behind, right;

    @Override
    public void runOpMode () {
        board = new Board (hardwareMap);
        followerWrapper = new PathFollowerWrapper(hardwareMap, startPose, 8);

       /* behind = new AverageDistanceSensor(hardwareMap.get(DistanceSensor.class,"behind"),
                DistanceUnit.INCH, 25);
        right = new AverageDistanceSensor(hardwareMap.get(DistanceSensor.class,"right"),
                DistanceUnit.INCH, 25);*/

        telemetry.addLine("Initialized");
        telemetry.update();

        onInit();

        while(opModeInInit()){
            //behind.update();
            //right.update();
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
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));

        while (followerWrapper.getFollower() != null && opModeIsActive()) { //Runs until end of path is reached
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
        //Sets the path for follower
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));

        double initBehind = behind.getDistance();
        double initRight = right.getDistance();

        while (followerWrapper.getFollower() != null && opModeIsActive() &&
                (Math.abs((behind.getDistance()-initBehind)-diff.x)<=2 ||
                Math.abs((right.getDistance()-initRight)-diff.y)<=2)) {
            //Runs until end of path is reached
            behind.update();
            right.update();
            followerWrapper.updatePose(board.getCurrentPose()); //Updates position
            double[] vectorCom = followerWrapper.follow(); //Gets the movement vector
            board.drive(vectorCom[0], -vectorCom[1], -vectorCom[2]); //Uses vector to power motors

            telemetry.addData("Behind", behind.getDistance());
            telemetry.addData("Right", right.getDistance());

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