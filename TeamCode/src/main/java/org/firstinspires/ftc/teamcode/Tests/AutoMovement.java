package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Autonomous.PathFollowerWrapper;
import org.firstinspires.ftc.teamcode.Board;

import java.util.Arrays;

import org.firstinspires.ftc.teamcode.Wheelie.Path;
import org.firstinspires.ftc.teamcode.Wheelie.Pose2D;

@Autonomous
public class AutoMovement extends LinearOpMode {

    //TODO add necessary rotations
    private PathFollowerWrapper followerWrapper;
    private Board board;

    private Pose2D start = new Pose2D(0,0,0);

    private Pose2D[] test = new Pose2D[] {
            new Pose2D(0,0,0),
            new Pose2D(24, 0, 0),
            new Pose2D(24, 24, 0)
    };
    @Override
    public void runOpMode() throws InterruptedException {
        board = new Board();
        board.init(hardwareMap);
        followerWrapper = new PathFollowerWrapper(hardwareMap, start, 8);

        waitForStart();

        followLoop(test, 5);

    }

    private void followLoop(Pose2D[] a, double waitTime){
        //Sets the path
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));

        //Will loop until robot has reached the path's end
        while(followerWrapper.getFollower() != null && opModeIsActive()){
            //Updating location
            followerWrapper.updatePose(
                    board.getDrivePosition(1),
                    board.getDrivePosition(2),
                    board.getAngle());
            //Finds the movement vector
            double[] vectorCom = followerWrapper.followPath();
            board.drive(vectorCom[0], vectorCom[1], vectorCom[2]);

            telemetry.addData("Position",
                    followerWrapper.getPose().x + ", " +
                            followerWrapper.getPose().y + ", " +
                            followerWrapper.getPose().h);
            telemetry.addData("Vector", Arrays.toString(vectorCom));

            telemetry.addLine();
            if(followerWrapper.getFollower() != null) {
                Pose2D goal = a[followerWrapper.getCurrentWayPoint() + 1];
                telemetry.addData("Goal",
                        goal.x + ", " +
                                goal.y + ", " +
                                goal.h);
            }

            telemetry.update();
        }

        //TODO give this its own function
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        while(time.time() < waitTime && opModeIsActive());

    }
}
