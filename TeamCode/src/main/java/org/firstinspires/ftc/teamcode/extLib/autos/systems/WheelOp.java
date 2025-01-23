package org.firstinspires.ftc.teamcode.extLib.autos.systems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

 public abstract class WheelOp extends LinearOpMode {
    protected PathFollowerWrapper followerWrapper;
    protected Board board;
    protected Pose2D startPose = new Pose2D(0,0,0);

    @Override
    public void runOpMode () {
        board = new Board (hardwareMap);
        followerWrapper = new PathFollowerWrapper(hardwareMap, startPose, 8);

        telemetry.addLine("Initialized");
        telemetry.update();

        onInit();

        waitForStart();

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

                telemetry.addData("Movement",
                        followerWrapper.followerValues().x + ", " +
                                followerWrapper.followerValues().y + ", " +
                                followerWrapper.followerValues().h);
            }

            telemetry.update();
        }
    }
    protected void maintain() {
        double[] vector = followerWrapper.maintainPos();
        board.drive(vector[0], -vector[1], -vector[2]);
    }

    protected void moveUntilTouch() {
        while (board.getTouched()) {
            followerWrapper.updatePose(board.getCurrentPose());
            final double[] vector = followerWrapper.maintainPos();
            board.drive(1, -vector[1], -vector[2]);

            telemetry.addData("Position",
                    followerWrapper.getPose().x + ", " +
                            followerWrapper.getPose().y + ", " +
                            followerWrapper.getPose().h);

            telemetry.update();
        }
        board.drive(0,0,0);
    }


    @Deprecated
    protected void setStartPose() {
        followerWrapper = new PathFollowerWrapper(hardwareMap, startPose, 8);
    }
}