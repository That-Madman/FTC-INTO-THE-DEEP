package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AutoThings.PathFollowerWrapper;
import org.firstinspires.ftc.teamcode.Board;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

@Autonomous
public class RedObserverAuto extends LinearOpMode {

    //TODO add necessary rotations
    private PathFollowerWrapper followerWrapper;
    private Board board = new Board();

    private Pose2D start = new Pose2D(0,0,0);

    private Pose2D[] toSub = new Pose2D[] {
            new Pose2D(0, 24, 0),
            new Pose2D(24, 24, 0),
            new Pose2D(24, 48, 0),
            new Pose2D(12, 48, 0),
    }, toObser = new Pose2D[] {
                new Pose2D(12, 48, 0),
                new Pose2D(72,48,0),
                new Pose2D(72, 0, 0),
    },backUp = new Pose2D[] {
            new Pose2D(72,0,0),
            new Pose2D(72, 24, 0),
    };
    @Override
    public void runOpMode() throws InterruptedException {
        board.init(hardwareMap);
        followerWrapper = new PathFollowerWrapper(hardwareMap, start, 8);

        waitForStart();

        followLoop(toSub, 5);
        //followLoop(toObser, 5);
        //followLoop(backUp, 5);

    }

    private void followLoop(Pose2D[] a, double waitTime){
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));

        while(followerWrapper.getFollower() != null && opModeIsActive()){
            followerWrapper.updatePose(board.getDrivePosition(1), board.getDrivePosition(2), board.getAngle());
            double[] powers = followerWrapper.followPath();

            board.setPowers(powers[0], powers[3], powers[1], powers[2]);

            telemetry.addData("Position", followerWrapper.getPose());
            telemetry.addData("Powers", Arrays.toString(powers));
            telemetry.update();
        }
        //TODO move this timer out of the function;
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        while(time.time() < waitTime && opModeIsActive());

    }
}
