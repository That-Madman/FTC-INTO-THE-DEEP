package org.firstinspires.ftc.teamcode.Auto.Park;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.WheelOp;

import Wheelie.Pose2D;

@Autonomous (group = "Park")
public class LeftPark extends WheelOp {
    final private Pose2D[] path = new Pose2D[]{
            new Pose2D(0, 0, 0),
            new Pose2D(5, 0, 0),
            new Pose2D(50, 0, Math.PI / 2),
            new Pose2D(50, 20, 0)
    };

    @Override
    public void run () {
        followLoop(path, 5);
    }
}
