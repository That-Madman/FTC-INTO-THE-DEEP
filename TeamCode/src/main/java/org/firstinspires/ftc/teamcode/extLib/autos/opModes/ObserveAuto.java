package org.firstinspires.ftc.teamcode.extLib.autos.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autos.systems.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class ObserveAuto extends WheelOp {

    private Pose2D[] forward = new Pose2D[]{
            new Pose2D(0,0,0),
            new Pose2D(19, 8,0)
    };

    private Pose2D[] backup = new Pose2D[]{
            new Pose2D(19, 4, 0),
            new Pose2D(3, 0, 0)
    };

    private Pose2D[] readyPush1 = new Pose2D[]{
            new Pose2D(3, 0, 0),
            new Pose2D(3, -25, 0),
            new Pose2D(47, -25, 0)
    };

    private Pose2D[] readyPush2 = new Pose2D[]{
            new Pose2D(47,-25,0),
            new Pose2D(50,-35,0)
    };

    private Pose2D[] push1 = new Pose2D[] {
            new Pose2D(50, -35, 0),
            new Pose2D(3, -35, 0)
    };

    private Pose2D[] readyPush3 = new Pose2D[] {
            new Pose2D(0, -27, 0),
            new Pose2D(25, -27, 0),
            new Pose2D(36, -27, 0)
    };

    private Pose2D[] push2 = new Pose2D[] {
            new Pose2D(36, -27, 0),
            new Pose2D(36, -36, 0),
            new Pose2D(2, -36, 0)
    };

    //Everything above works
    //Stops at observation second time

    private Pose2D[] strafeToScore = new Pose2D[]{
            new Pose2D(2, -27, 0),
            new Pose2D(2, 8, 0),
            new Pose2D(19, 8, 0)
    };

    @Override
    public void onInit() {
        board.setBigGrab(true);
        board.setTinyGrab(true);
    }

    @Override
    public void run() {
        board.setRot((byte) 4);
        board.setLift(850);

       followPath(forward);
        moveUntilTouch();

        board.setRot((byte) 3);
        sleep(1000);

        board.setTinyGrab(false);
        board.setBigGrab(false);

        board.setLift(0);
        board.drive(0,0,0);

        followPath(backup);

        followPath(readyPush1);
        followPath(readyPush2);

        followPath(push1);
    }
}