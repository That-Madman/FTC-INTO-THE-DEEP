package org.firstinspires.ftc.teamcode.Autos;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Wheelie.Pose2D;

@Autonomous
public class ObserveAuto extends WheelOp {
    private Pose2D[] path1 = new Pose2D[]{
            new Pose2D(0, 0, 0),
            new Pose2D(15, 4, 0),
            new Pose2D(22, 4, 0)
    };

    private Pose2D[] path2 = new Pose2D[]{
            new Pose2D(22, -4, 0),
            new Pose2D(15, -4, 0),
            new Pose2D(3, 0, 0)
    };

    private Pose2D[] path3 = new Pose2D[]{
            new Pose2D(3, 0, 0),
            new Pose2D(36, -12, 0),
            new Pose2D(50, -24, 0)
    };

    private Pose2D[] turn1 = new Pose2D[] {
            new Pose2D(50, -24, 0),
            new Pose2D(50, -24, Math.toRadians(90)),
            new Pose2D(50, -24, Math.toRadians(180))
    };

    private Pose2D[] path5 = new Pose2D[]{
            new Pose2D(50,-24,0),
            new Pose2D(50,-29,Math.toRadians(90)),
            new Pose2D(50,-30,Math.toRadians(180))
    };

    private Pose2D[] path6 = new Pose2D[] {
            new Pose2D(50, -30, Math.toRadians(180)),
            new Pose2D(25, -30, Math.toRadians(180)),
            new Pose2D(0, -30, Math.toRadians(180))
    };

    private Pose2D[] path7 = new Pose2D[] {
            new Pose2D(0, -30, Math.toRadians(180)),
            new Pose2D(25, -34, Math.toRadians(180)),
            new Pose2D(54, -36, Math.toRadians(180))
    };

    private Pose2D[] path9 = new Pose2D[] {
            new Pose2D(54, -39, Math.toRadians(180)),
            new Pose2D(25, -39, Math.toRadians(180)),
            new Pose2D(2, -39, Math.toRadians(180))
    };

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
    public void run() {
        followPath(path1);
        sleep(1000);
        followPath(path2);
        sleep(1000);
        followPath(path3);
        sleep(1000);
        followPath(turn1);
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
        }
    }
}