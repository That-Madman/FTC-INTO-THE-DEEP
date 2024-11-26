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
            new Pose2D(0, 0, 0),
            new Pose2D(-23, 4, 0),
    };

    private Pose2D[] path4 = new Pose2D[]{
            new Pose2D(0, 0, 0),
            //new Pose2D(0, 0, Math.toRadians(90))
    };
/*
    private Pose2D[] path3 = new Pose2D[]{
            new Pose2D(0, 0, 0),
            new Pose2D(48, 0, 0)
    };*/

    @Override
    public void run() {
        followPath(path1, 0);
        sleep(5000);
        followPath(path2, 0);
        sleep(5000);
        followPath(path4, 0);
        //followPath(path3, 0);
    }
}
