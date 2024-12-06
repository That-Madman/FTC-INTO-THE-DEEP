package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.extLib.WheelOp;

import Wheelie.Pose2D;

public class PiTest extends WheelOp {
    Pose2D[] forward = new Pose2D[]{
            new Pose2D(0,0,0),
            new Pose2D(12, 0, 0),
            new Pose2D(24, 0, 0)
    },
    rotate = new Pose2D[]{
            new Pose2D(24, 0, 0),
            new Pose2D(24, 0, Math.toRadians(90)),
            new Pose2D(24, 0, Math.toRadians(180))
    },
    back = new Pose2D[]{
            new Pose2D(24,0, Math.toRadians(180)),
            new Pose2D(12, 0, Math.toRadians(180)),
            new Pose2D(0, 0, Math.toRadians(180))
    },
    forRotate =  new Pose2D[]{
            new Pose2D(0,0,0),
            new Pose2D(12, 0, Math.toRadians(90)),
            new Pose2D(24, 0, Math.toRadians(180))
    };

    @Override
    public void run() {
        //followPath(forRotate,0);

        followPath(forward,0);
        followPath(rotate,0);
        followPath(back,0);
    }
}
