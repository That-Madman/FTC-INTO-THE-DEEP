package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.extLib.Board;
import org.firstinspires.ftc.teamcode.extLib.wheelieExt.PathFollowerWrapper;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

@Autonomous
public class StraightTest extends LinearOpMode {
    protected PathFollowerWrapper followerWrapper;
    protected Board board;
    public ElapsedTime time;
    SparkFunOTOS sparkFunOTOS;

    Pose2D[] points = new Pose2D[] {
            new Pose2D(0,0,0),
            new Pose2D(50, 0,0),
            new Pose2D(100, 0,0)
    };

    @Override
    public void runOpMode () {
        time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        board = new Board (hardwareMap);
        sparkFunOTOS = hardwareMap.get(SparkFunOTOS.class, "otos");
        board.resetIMU();
        followerWrapper = new PathFollowerWrapper(hardwareMap, new Pose2D(0,0), 8);

        configureOTOS();

        waitForStart();

        followPath(points, 0);

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
            SparkFunOTOS.Pose2D pos = sparkFunOTOS.getPosition();
            followerWrapper.updatePose(new Pose2D(pos.x, pos.y, pos.h)); //Updates position
            double[] vectorCom = followerWrapper.followPathPID(); //Gets the movement vector
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
        //followerWrapper.resetPose();
        //followerWrapper.resetPose(new Pose2D(0,0, AngleUnit.normalizeRadians(-board.getAngle())));
        time.reset();
        while (time.time() < waitTime && opModeIsActive()); //TODO: Why is this here
    }


    private void configureOTOS(){
        sparkFunOTOS.setLinearUnit(DistanceUnit.INCH);
        sparkFunOTOS.setAngularUnit(AngleUnit.RADIANS);
        sparkFunOTOS.setOffset(new SparkFunOTOS.Pose2D(3.5, 6, 0));
        sparkFunOTOS.setLinearScalar(100./92.5117);
        sparkFunOTOS.setAngularScalar(Math.toRadians(2160.0)/Math.toRadians(2175.0));
        sparkFunOTOS.resetTracking();
        sparkFunOTOS.setPosition(new SparkFunOTOS.Pose2D(0,0,0));
        sparkFunOTOS.calibrateImu(255, false);
    }
}
