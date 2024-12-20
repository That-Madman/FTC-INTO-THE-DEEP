package org.firstinspires.ftc.teamcode.autos;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class BluePlace extends WheelOp {
    private Pose2D[] path1 = new Pose2D[]{
            new Pose2D(0, 0, 0),
            new Pose2D(0, 4, 0),
            new Pose2D(25, 4, 0)
    };

    private Pose2D[] path2 = new Pose2D[]{
            new Pose2D(25, 4, 0),
            new Pose2D(5, 0, 0),
            new Pose2D(5, 0, 0)
    };

    private Pose2D[] path3 = new Pose2D[]{
            new Pose2D(7, 0, 0),
            new Pose2D(7, -12, 0),
            new Pose2D(7, -26, 0)
    };

    private Pose2D[] path4 = new Pose2D[]{
            new Pose2D(7, -26,  0),
            new Pose2D(36, -26,  0),
            new Pose2D(54, -26,  0)

    };

    private Pose2D[] path5 = new Pose2D[]{
            new Pose2D(54,-26,0),
            new Pose2D(54,-38,0),
            new Pose2D(54,-40,0)
    };

    private Pose2D[] path6 = new Pose2D[] {
            new Pose2D(54, -40, 0),
            new Pose2D(25, -40, 0),
            new Pose2D(2, -40, 0)
    };

    private Pose2D[] path7 = new Pose2D[] {
            new Pose2D(4, -40, 0),
            new Pose2D(4, -30, 0),
            new Pose2D(4, -26, 0)
    };

    private Pose2D[] path8 = new Pose2D[] {
            new Pose2D(4, -26, 0),
            new Pose2D(36, -26,  0),
            new Pose2D(54, -26,  0)
    };

    private Pose2D[] path9 = new Pose2D[]{
            new Pose2D(54, -26, 0),
            new Pose2D(54, -35, 0),
            new Pose2D(54, -52, 0)
    };

    private Pose2D[] path10 = new Pose2D[]{
            new Pose2D(54, -52, 0),
            new Pose2D(25, -52, 0),
            new Pose2D(2, -52, 0)
    };

    private Pose2D[] path11 = new Pose2D[]{
            new Pose2D(2, -52, 0),
            new Pose2D(3, -52, 0),
            new Pose2D(5, -52, 0),
            new Pose2D(6, -52, 0)
    };

    private Pose2D[] path12 = new Pose2D[] {
            new Pose2D(6, -52, 0),
            new Pose2D(6, -25, 0),
            new Pose2D(6, 0, 0)
    };

    private Pose2D[] path13 = new Pose2D[]{
            new Pose2D(6, 5, 0),
            new Pose2D(18, 5, 0),
            new Pose2D(26, 5, 0)
    };

    private Pose2D[] path14 = new Pose2D[] {
            new Pose2D(26, 5, 0),
            new Pose2D(18, 5, 0),
            new Pose2D(6,5,0)
    };

    private Pose2D[] path15 = new Pose2D[] {
            new Pose2D(6, 5, 0),
            new Pose2D(6, -30,0),
            new Pose2D(6, -32, 0)
    };

    private Pose2D[] path16 = new Pose2D[] {
            new Pose2D(6, -32, 0),
            new Pose2D(36,-32 ,0),
            new Pose2D(60, -32, 0)
    };

    private Pose2D[] path17 = new Pose2D[] {
            new Pose2D(56, -32, 0),
            new Pose2D(56, -28,0),
            new Pose2D(56, -16, 0)
    };


    @Override
    public void run() {
        followPath(path1, 0);
        sleep(2000);
        followPath(path2, 0);
        sleep(2000);
        followPath(path3, 0);
        sleep(2000);
        followPath(path4, 0);
        sleep(2000);
        followPath(path5, 0);
        sleep(2000);
        followPath(path6, 0);
        sleep(2000);
        followPath(path7, 0);
        sleep(2000);
        followPath(path8, 0);
        sleep(2000);
        followPath(path9, 0);
        sleep(2000);
        followPath(path10, 0);
        sleep(2000);
        followPath(path11, 0);
        sleep(2000);
        followPath(path12, 0);
        sleep(2000);
        followPath(path13, 0);
        sleep(2000);
        followPath(path14, 0);
        sleep(2000);
        followPath(path15, 0);
        sleep(2000);
        followPath(path16, 0);
        sleep(2000);
        followPath(path17, 0);
    }
}
