package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class Right extends WheelOp {
    private Pose2D[] p = new Pose2D[]{
        new Pose2D(0, 0, 0),
        new Pose2D(6, 0, Math.toRadians(45)),
        new Pose2D(12, 0, Math.toRadians(90)),
        new Pose2D(24, 0, Math.toRadians(180))
    };

    @Override
    public void run () {
        followPath(p, 0);
    }
}