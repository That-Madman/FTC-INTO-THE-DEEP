package org.firstinspires.ftc.teamcode.tests;

import androidx.annotation.AnimatorRes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.WheelOp;

import Wheelie.Pose2D;

@Autonomous
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

    moreRotate = new Pose2D[] {
            new Pose2D(0, 0, 180),
            new Pose2D(0,0, 90),
            new Pose2D(0,0,0)
    };

    @Override
    public void run() {
        //followPath(forRotate,0);
        while (opModeIsActive()) {
            followPath(forward, 0);
            followPath(rotate, 0);
            followPath(back, 0);
            followPath(moreRotate, 0);
        }
    }
}
