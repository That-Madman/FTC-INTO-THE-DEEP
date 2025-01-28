package org.firstinspires.ftc.teamcode.extLib.autos.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autos.systems.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class NetAuto extends WheelOp {
    Pose2D[] toBucket1 = new Pose2D[] {
            new Pose2D(5, 0),
            new Pose2D(5, 20),
            new Pose2D(2, 20)
    };

    @Override
    public void onInit() {
        board.setBigGrab(true);
        board.setTinyGrab(true);
    }

    @Override
    public void run() {
        board.setRot((byte) 2);
        board.setLift(2700);

        followPath(toBucket1);

        board.setBigGrab(false);
        board.setTinyGrab(false);
        sleep(500);
    }
}
