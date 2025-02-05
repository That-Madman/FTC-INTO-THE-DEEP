package org.firstinspires.ftc.teamcode.autonomous.oneAndDone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autoSystems.WheelOp;

import Wheelie.Pose2D;

@Autonomous(group = "One and Done")
public class NetOne extends WheelOp {
   Pose2D[] toBucket1 = new Pose2D[] {
            new Pose2D(5, 0),
            new Pose2D(5, 20),
            new Pose2D(2, 20)
    };

    Pose2D[] toBucket2 = new Pose2D[] {
            new Pose2D(4, 18),
            new Pose2D(2, 20)
    };

    @Override
    public void onInit() {
        board.setBigGrab(true);
        board.setTinyGrab(true);
    }

    @Override
    public void run() {
        /*
        board.setAscentScrews(1);
        sleep(1000);
        board.setAscentScrews(0);
        
         */

        board.setRot((byte) 2);
        board.setLift(2700);

        followPath(toBucket1);

        board.setBigGrab(false);
        board.setTinyGrab(false);
        sleep(500);

        board.setLift(0);
    }
}
