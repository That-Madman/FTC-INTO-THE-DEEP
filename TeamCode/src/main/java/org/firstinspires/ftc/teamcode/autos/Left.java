package org.firstinspires.ftc.teamcode.autos;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class Left extends WheelOp {
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
            new Pose2D(3, -12, 0),
            new Pose2D(3, -24, 0)
    };

    private Pose2D[] path4 = new Pose2D[]{
            new Pose2D(3, -24,  0),
            new Pose2D(36, -24,  0),
            new Pose2D(50, -24,  0)

    };

    private Pose2D[] turn1 = new Pose2D[] {
            new Pose2D(50, -24, 0),
            new Pose2D(50, -24, Math.toRadians(90)),
            new Pose2D(50, -24, Math.toRadians(180))
    };

    private Pose2D[] path5 = new Pose2D[]{
            new Pose2D(50,-24,Math.toRadians(180)),
            new Pose2D(50,-29,Math.toRadians(180)),
            new Pose2D(50,-30,Math.toRadians(180))
    };

    private Pose2D[] path6 = new Pose2D[] {
            new Pose2D(50, -30, Math.toRadians(180)),
            new Pose2D(25, -30, Math.toRadians(180)),
            new Pose2D(0, -30, Math.toRadians(180))
    };

    private Pose2D[] path7 = new Pose2D[] {
            new Pose2D(0, -30, Math.toRadians(180)),
            new Pose2D(25, -30, Math.toRadians(180)),
            new Pose2D(54, -30, Math.toRadians(180))
    };

    private Pose2D[] pathnot7 = new Pose2D[] {
            new Pose2D(54, -30, Math.toRadians(180)),
            new Pose2D(54, -34, Math.toRadians(180)),
            new Pose2D(54, -36, Math.toRadians(180))
    };

    private Pose2D[] path8 = new Pose2D[] {
            new Pose2D(54, -36, Math.toRadians(180)),
            new Pose2D(54, -36, Math.toRadians(180)),
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
    //skip
    private Pose2D[] path11 = new Pose2D[]{
            new Pose2D(20,-39, Math.toRadians(180)),
            new Pose2D(20, -39, Math.toRadians(90)),
            new Pose2D(20, -39, 0)
    };

    private Pose2D[] path12 = new Pose2D[]{
            new Pose2D(20, -39, Math.toRadians(180)),
            new Pose2D(12, -20, Math.toRadians(180)),
            new Pose2D(8, 6, Math.toRadians(180))
    };

    private Pose2D[] pathnot12 = new Pose2D[] {
            new Pose2D(8, 6, Math.toRadians(180)),
            new Pose2D(8,6, Math.toRadians(90)),
            new Pose2D(8, 6, 0)
    };

    private Pose2D[] path13 = new Pose2D[]{
            new Pose2D(8, 6, 0),
            new Pose2D(16, 6, 0),
            new Pose2D(25, 6, 0)
    };

    private Pose2D[] path14 = new Pose2D[] {
            new Pose2D(25, 6, 0),
            new Pose2D(16, 6, 0),
            new Pose2D(8, 6, 0)
    };

    private Pose2D[] path15 = new Pose2D[] {
            new Pose2D(8, 6,0),
            new Pose2D(8, -26, 0),
            new Pose2D(8, -39, 0)
    };

    private Pose2D[] path16 = new Pose2D[] {
            new Pose2D(8,-39, 0),
            new Pose2D(20, -39, 0),
            new Pose2D(40, -39, 0)
    };

    private Pose2D[] path17 = new Pose2D[] {
            new Pose2D(40, -39, 0),
            new Pose2D(40, -39, Math.toRadians(45)),
            new Pose2D(40, -39, Math.toRadians(90))
    };

    private Pose2D[] path18 = new Pose2D[] {
            new Pose2D(40, -40, Math.toRadians(90)),
            new Pose2D(40,-45, Math.toRadians(90)),
            new Pose2D(40, -50, Math.toRadians(90))
    };

    private Pose2D[] path19 = new Pose2D[] {
            new Pose2D(40, -50, Math.toRadians(90)),
            new Pose2D(45, -54, Math.toRadians(90)),
            new Pose2D(50, -56, Math.toRadians(90))
    };
    @Override
    public void run() {
        followPath(path1, 0);
        sleep(1000);
        followPath(path2, 0);
        sleep(1000);
        followPath(path3, 0);
        sleep(1000);
        followPath(path4, 0);
        sleep(1000);
        followPath(turn1, 0);
        sleep(1000);
        followPath(path5, 0);
        sleep(1000);
        followPath(path6, 0);
        sleep(1000);
        followPath(path7, 0);
        sleep(1000);
        followPath(pathnot7, 0);
        sleep(1000);
        followPath(path8, 0);
        sleep(1000);
        followPath(path9, 0);
        sleep(1000);
        followPath(path10, 0);
        sleep(1000);
        //followPath(path11, 0);
        //sleep(2000);
        followPath(path12, 0);
        sleep(1000);
        followPath(pathnot12, 0);
        sleep(1000);
        followPath(path13, 0);
        sleep(1000);
        followPath(path14, 0);
        sleep(1000);
        followPath(path15, 0);
        sleep(1000);
        followPath(path16, 0);
        sleep(1000);
        followPath(path17, 0);
        sleep(10000);
        followPath(path18, 0);
        sleep(1000);
        //followPath(path19, 0);

        while(opModeIsActive()) {
            telemetry.addLine();
        }
    }
}
