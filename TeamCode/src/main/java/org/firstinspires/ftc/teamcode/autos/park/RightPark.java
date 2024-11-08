package org.firstinspires.ftc.teamcode.autos.park;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.WheelOp;

import Wheelie.Pose2D;

@Autonomous(group = "Park")
public class RightPark extends WheelOp {
    private final Pose2D[] path = new Pose2D[]{
            new Pose2D(0, 0, 0),
            new Pose2D(50, 0, 0),
            new Pose2D(50, -10, 0)
    };

    @Override
    public void run() {
        followLoop(path, 5);
    }
}