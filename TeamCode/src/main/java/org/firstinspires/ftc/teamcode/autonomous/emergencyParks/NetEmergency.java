package org.firstinspires.ftc.teamcode.autonomous.emergencyParks;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autoSystems.WheelOp;

import Wheelie.Pose2D;

@Autonomous(group = "Emergency")
public class NetEmergency extends WheelOp {
    final Pose2D[] park = new Pose2D[] {
            new Pose2D(6, 0),
            new Pose2D(6, 44),
            new Pose2D(3, 44)
    };

    @Override
    public void onInit() {}

    @Override
    public void run() {
        followPath(park);
    }
}
