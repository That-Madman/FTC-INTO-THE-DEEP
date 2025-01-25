package org.firstinspires.ftc.teamcode.extLib.autos.opModes.unstable;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autos.systems.WheelOp;

import Wheelie.Pose2D;

@Autonomous (group = "UNSTABLE")
public class UnstableObserveAuto extends WheelOp {
    final private Pose2D[] path1 = new Pose2D[]{
            new Pose2D(0, 0, 0),
            new Pose2D(15, 4, 0),
            new Pose2D(22, 4, 0)
    };

    final private Pose2D[] path2 = new Pose2D[]{
            new Pose2D(22, -4, 0),
            new Pose2D(15, -4, 0),
            new Pose2D(3, 0, 0)
    };

    final private Pose2D[] path3 = new Pose2D[]{
            new Pose2D(3, 0, 0),
            new Pose2D(36, -12, 0),
            new Pose2D(50, -24, 0)
    };

    final private Pose2D[] turn1 = new Pose2D[] {
            new Pose2D(50, -24, 0),
            new Pose2D(50, -24, Math.toRadians(90)),
            new Pose2D(50, -24, Math.toRadians(180))
    };

    final private Pose2D[] path5 = new Pose2D[]{
            new Pose2D(50,-24,0),
            new Pose2D(50,-29,Math.toRadians(90)),
            new Pose2D(50,-30,Math.toRadians(180))
    };

    final private Pose2D[] path6 = new Pose2D[] {
            new Pose2D(50, -30, Math.toRadians(180)),
            new Pose2D(25, -30, Math.toRadians(180)),
            new Pose2D(0, -30, Math.toRadians(180))
    };

    final private Pose2D[] path7 = new Pose2D[] {
            new Pose2D(0, -30, Math.toRadians(180)),
            new Pose2D(25, -34, Math.toRadians(180)),
            new Pose2D(54, -36, Math.toRadians(180))
    };

    final private Pose2D[] path9 = new Pose2D[] {
            new Pose2D(54, -39, Math.toRadians(180)),
            new Pose2D(25, -39, Math.toRadians(180)),
            new Pose2D(2, -39, Math.toRadians(180))
    };

    final private Pose2D[] path10 = new Pose2D[] {
            new Pose2D(2, -39, Math.toRadians(180)),
            new Pose2D(15, -39, Math.toRadians(180)),
            new Pose2D(20, -39, Math.toRadians(180))
    };

    final private Pose2D[] path12 = new Pose2D[]{
            new Pose2D(20, -39, Math.toRadians(180)),
            new Pose2D(12, -20, Math.toRadians(90)),
            new Pose2D(8, 6, 0)
    };

    final private Pose2D[] path13 = new Pose2D[]{
            new Pose2D(8, 6, 0),
            new Pose2D(16, 6, 0),
            new Pose2D(25, 6, 0)
    };

    final private Pose2D[] path14 = new Pose2D[] {
            new Pose2D(25, 6, 0),
            new Pose2D(16, -26, 0),
            new Pose2D(8, -39, 0)
    };

    final private Pose2D[] path16 = new Pose2D[] {
            new Pose2D(8,-39, 0),
            new Pose2D(20, -39, Math.toRadians(45)),
            new Pose2D(40, -39, Math.toRadians(90))
    };

    final private Pose2D[] path18 = new Pose2D[] {
            new Pose2D(40, -39, Math.toRadians(90)),
            new Pose2D(10,-39, Math.toRadians(90)),
            new Pose2D(-4, -39, Math.toRadians(90))
    };

    @Override
    public void onInit() {
        board.setBigGrab(true);
        board.setTinyGrab(true);
    }

    @Override
    public void run () {
        board.setRot((byte) 4);
        board.setLift(850);

        followPath(path1);
        sleep(1000);

        moveUntilTouch();

        board.setRot((byte) 3);
        sleep(1000);

        board.setTinyGrab(false);
        board.setBigGrab(false);

        board.setLift(0);

        board.drive(0,0,0);

        followPath(path2);

        followPath(path5);

        //TODO: TEMP STOP
        while(opModeIsActive()) {
            telemetry.addLine();
        }

        followPath(path6);

        followPath(path7);

        followPath(path9);

        followPath(path10);

        followPath(path12);

        followPath(path13);

        followPath(path14);

        followPath(path16);

        followPath(path18);

        while(opModeIsActive()) {
            telemetry.addLine();
        }
    }
}