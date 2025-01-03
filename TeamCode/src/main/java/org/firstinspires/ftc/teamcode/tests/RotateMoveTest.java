package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.WheelOp;
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
    public void run() {
        followPath(path1, 0);
        followPath(path2, 0);
    }
}