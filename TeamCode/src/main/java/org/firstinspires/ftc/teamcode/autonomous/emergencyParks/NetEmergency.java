package org.firstinspires.ftc.teamcode.autonomous.emergencyParks;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.extLib.autoSystems.WheelOp;

import Wheelie.Pose2D;

@Autonomous(group = "Emergency")
public class NetEmergency extends WheelOp {
    final Pose2D[] park = new Pose2D[] {
            new Pose2D(5, 15),
            new Pose2D(22, 40),
            new Pose2D(52, 29),
            new Pose2D(55, 14, Math.toRadians(90)),
            new Pose2D(53, 20, Math.toRadians(90)),
            new Pose2D(53, 10, Math.toRadians(90))
    };

    @Override
    public void onInit() {}

    @Override
    public void run() {
        followPath(park);
    }
}
