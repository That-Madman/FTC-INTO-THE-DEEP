package org.firstinspires.ftc.teamcode.extLib.autos.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autos.systems.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class ObserveAuto extends WheelOp {

    private Pose2D[] forward = new Pose2D[]{
            new Pose2D(0,0,0),
            new Pose2D(19, 4,0)
    };

    private Pose2D[] backup = new Pose2D[]{
            new Pose2D(19, 4, 0),
            new Pose2D(3, 0, 0)
    };

    private Pose2D[] readyPush1 = new Pose2D[]{
            new Pose2D(3, 0, 0),
            new Pose2D(3, -20, 0),
            new Pose2D(36, -20, 0)
    };

    private Pose2D[] readyPush2 = new Pose2D[]{
            new Pose2D(36,-20,0),
            new Pose2D(36,-27,0)
    };

    private Pose2D[] push1 = new Pose2D[] {
            new Pose2D(36, -27, 0),
            new Pose2D(0, -27, 0)
    };

    private Pose2D[] readyPush3 = new Pose2D[] {
            new Pose2D(0, -36, 0),
            new Pose2D(25, -36, 0),
            new Pose2D(36, -36, 0)
    };

    private Pose2D[] push2 = new Pose2D[] {
            new Pose2D(36, -36, 0),
            new Pose2D(2, -36, 0)
    };

    //Everything above works
    //Stops at observation second time

    private Pose2D[] strafeToScore = new Pose2D[]{
            new Pose2D(2, -27, 0),
            new Pose2D(2, 4, 0),
            new Pose2D(19, 4, 0)
    };

    @Override
    public void onInit() {
        //startPose = new Pose2D(0,0, Math.PI);
        //setStartPose();
    }

    @Override
    public void run() {
        // Moves to place preload
        followPath(forward);
        moveUntilTouch();

        //TODO: activate servos for preload

        // Backs up
        followPath(backup);

        // pushes the first spiked sample
        followPath(readyPush1);
        followPath(readyPush2);
        followPath(push1);

        // Pushes the second spiked
        followPath(readyPush3);
        followPath(push2);

        // Picks up and scores human player specimen
        //TODO: activate servos for pick up
        followPath(strafeToScore);
        moveUntilTouch();
        //TODO: activate servos for scoring

        // Either park or go for second specimen
    }
}