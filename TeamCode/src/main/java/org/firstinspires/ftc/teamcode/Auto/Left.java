package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AutoThings.PathFollowerWrapper;
import org.firstinspires.ftc.teamcode.Board;
import org.firstinspires.ftc.teamcode.SpoolStates;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.Pose2D;

public class Left extends LinearOpMode {
    private PathFollowerWrapper followerWrapper;
    private Board board = new Board();

    private Pose2D start = new Pose2D(0, 0, 0);

    private Pose2D[] path1 = new Pose2D [] {
            new Pose2D (0,0),
    };

    private Pose2D[] path2 = new Pose2D [] {
            new Pose2D(0,0),
    };

    private Pose2D[] path3 = new Pose2D[] {
            new Pose2D(0,0),
    };

    @Override
    public void runOpMode () {
        board.init(hardwareMap);
        followerWrapper = new PathFollowerWrapper(hardwareMap, start, 8);
        board.autoSpoolOverride();

        waitForStart();

        board.setSweep(0.5);
        board.setSpoolPos(SpoolStates.LIFTEDABIT);

        followLoop(path1, 5);

        board.spoolTo(SpoolStates.BUCKETHIGH);

        followLoop(path2, 5);

        board.setSweep(-0.5);
        sleep(1000);
        board.setSweep(0);

        followLoop(path3, 5);
    }

    private void followLoop(Pose2D[] a, final double waitTime) {
        followerWrapper.setPath(followerWrapper.getPose(), new Path(followerWrapper.getPose(), a));

        while (followerWrapper.getFollower() != null && opModeIsActive()) {
            followerWrapper.updatePose(
                    board.getAngle());
            double[] vectorCom = followerWrapper.followPath();
            board.drive(vectorCom[0], vectorCom[1], vectorCom[2]);

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
        //TODO move this timer out of the function;
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        while (time.time() < waitTime && opModeIsActive());
    }
}
