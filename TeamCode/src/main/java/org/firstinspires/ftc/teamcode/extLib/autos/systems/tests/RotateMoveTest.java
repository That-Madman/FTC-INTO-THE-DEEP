package org.firstinspires.ftc.teamcode.extLib.autos.systems.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autos.systems.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class RotateMoveTest extends WheelOp {

    Pose2D[] path1 = new Pose2D[]{
            new Pose2D(0,0,0),
            new Pose2D(24, 0, Math.toRadians(90)),
            new Pose2D(24, 24, Math.toRadians(180))
    },
            path2 = new Pose2D[]{
                    new Pose2D(24, 24, Math.toRadians(180)),
                    new Pose2D(0,0,0)
            };

    @Override
    public void onStart() {

    }

    @Override
    public void run() {
        followPath(path1);
        followPath(path2);
    }
}