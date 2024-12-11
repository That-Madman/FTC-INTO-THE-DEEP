package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class Right extends WheelOp {
    private Pose2D[] p = new Pose2D[]{
        new Pose2D(0, 0, 0),
        new Pose2D(0, 0, Math.toRadians(45)),
        new Pose2D(0, 0, Math.toRadians(90)),
        new Pose2D(0, 0, Math.toRadians(180))
    };

    private Pose2D[] p2 = new Pose2D[] {
            new Pose2D(0,0, Math.toRadians(180)),
            new Pose2D(0,0,Math.toRadians(90)),
            new Pose2D(0,0, 0),
    };

    @Override
    public void run () {
        followPath(p, 0);
        sleep(1000);
        followPath(p2, 0);
    }

}