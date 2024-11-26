package org.firstinspires.ftc.teamcode.autos;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class BluePlace extends WheelOp {
    private Pose2D[] path1 = new Pose2D[]{
            new Pose2D(0, 0, 0),
            new Pose2D(0, -4, 0),
            new Pose2D(30, -4, 0)
    };

    private Pose2D[] path2 = new Pose2D[]{
            new Pose2D(30, -4, 0),
            new Pose2D(7, 0, 0),
            new Pose2D(7, 0, 0)
    };

    private Pose2D[] path3 = new Pose2D[]{
            new Pose2D(7, 0, 0),
            new Pose2D(7, 12, 0),
            new Pose2D(7, 24, 0)
    };

    private Pose2D[] path4 = new Pose2D[]{
            new Pose2D(7, 24,  0),
            new Pose2D(36, 24,  0),
            new Pose2D(56, 24,  0)

    };

    private Pose2D[] path5 = new Pose2D[]{
            new Pose2D(56,-12,0),
            new Pose2D(56,-24,0),
            new Pose2D(56,-32,0)
    };

    private Pose2D[] path6 = new Pose2D[] {
            new Pose2D(56, 48, 0),
            new Pose2D(24, 48, 0),
            new Pose2D(7, 48, 0)
    };

    private Pose2D[] path7 = new Pose2D[] {
            new Pose2D(7, 48, 0),
            new Pose2D(7, 24, 0),
            new Pose2D(7, 0, 0)
    };

    /* private Pose2D[] path5 = new Pose2D[] {
            new Pose2D(
    };
     */

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
        /*sleep(2000);
        followPath(path6, 0);
        sleep(2000);
        //followPath(path7, 0);*/
    }
}
