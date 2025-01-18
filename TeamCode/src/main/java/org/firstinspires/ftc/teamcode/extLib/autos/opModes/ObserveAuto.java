package org.firstinspires.ftc.teamcode.extLib.autos.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autos.systems.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class ObserveAuto extends WheelOp {

    private Pose2D[] forward = new Pose2D[]{
            new Pose2D(0,0,0),
            new Pose2D(19, 04,0)
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

    private Pose2D[] path7 = new Pose2D[] {
            new Pose2D(0, -27, 0),
            new Pose2D(25, -27, 0),
            new Pose2D(36, -27, 0)
    };

    private Pose2D[] path9 = new Pose2D[] {
            new Pose2D(36, -27, 0),
            new Pose2D(2, -27, 0)
    };

    //Everything above works
    //Stops at observation second time

    private Pose2D[] path10 = new Pose2D[] {
            new Pose2D(2, -39, Math.toRadians(180)),
            new Pose2D(15, -39, Math.toRadians(180)),
            new Pose2D(20, -39, Math.toRadians(180))
    };

    private Pose2D[] path12 = new Pose2D[]{
            new Pose2D(20, -39, Math.toRadians(180)),
            new Pose2D(12, -20, Math.toRadians(90)),
            new Pose2D(8, 6, 0)
    };

    private Pose2D[] path13 = new Pose2D[]{
            new Pose2D(8, 6, 0),
            new Pose2D(16, 6, 0),
            new Pose2D(25, 6, 0)
    };

    private Pose2D[] path14 = new Pose2D[] {
            new Pose2D(25, 6, 0),
            new Pose2D(16, -26, 0),
            new Pose2D(8, -39, 0)
    };

    private Pose2D[] path16 = new Pose2D[] {
            new Pose2D(8,-39, 0),
            new Pose2D(20, -39, Math.toRadians(45)),
            new Pose2D(40, -39, Math.toRadians(90))
    };

    private Pose2D[] path18 = new Pose2D[] {
            new Pose2D(40, -39, Math.toRadians(90)),
            new Pose2D(10,-39, Math.toRadians(90)),
            new Pose2D(-4, -39, Math.toRadians(90))
    };

    @Override
    public void onStart() {
        //startPose = new Pose2D(0,0, Math.PI);
        //setStartPose();
    }

    @Override
    public void run() {
        followPath(forward);
        sleep(1000);

        //TODO: activate servos for preload

       followPath(backup);
        sleep(1000);

         followPath(readyPush1);
        sleep(1000);

        followPath(readyPush2);
        sleep(1000);

        followPath(push1);
        sleep(1000);

        followPath(path7);
        sleep(1000);

        followPath(path9);
        sleep(1000);

        /*followPath(turn1);
        sleep(1000);
        followPath(path5);
        sleep(1000);
        followPath(path6);
        sleep(1000);
        followPath(path7);
        sleep(1000);
        followPath(path9);
        sleep(1000);
        followPath(path10);
        sleep(1000);
        followPath(path12);
        sleep(1000);
        followPath(path13);
        sleep(1000);
        followPath(path14);
        sleep(1000);
        followPath(path16);
        sleep(1000);
        followPath(path18);

        while(opModeIsActive()) {
            telemetry.addLine();
        }*/
    }
}