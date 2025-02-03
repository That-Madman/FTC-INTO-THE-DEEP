package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autoSystems.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class NetAuto extends WheelOp {
    Pose2D[] toBucket1 = new Pose2D[] {
            new Pose2D(5, 0),
            new Pose2D(5, 20),
            new Pose2D(2, 20)
    };

    Pose2D[] grab1 = new Pose2D[] {
            new Pose2D(4,18),
            new Pose2D(6,16),
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
        board.setRot((byte) 2);
        board.setLift(2700);

        followPath(toBucket1);

        board.setBigGrab(false);
        board.setTinyGrab(false);
        sleep(500);

        board.setLift(0);

        followPath(grab1);

        board.setReach((byte) 1);

        board.setPick(false);
        board.setSwivel((byte) 2);

        { //Block of Handoff
            board.setRot((byte) 0);
            board.setBigGrab(false);
            board.setTinyGrab(false);

            board.setPick(false);
            sleep(150);

            board.setReach((byte) 1);
            sleep(150);

            board.setSwivel((byte) 0);
            sleep(150);

            board.setReach((byte) 0);
            sleep(150);

            board.setTinyGrab(true);
            sleep(150);

            board.setPick(true);
            sleep(150);

            board.setReach((byte) 1);
            sleep(150);

            board.setBigGrab(true);
            sleep(150);

            board.setRot((byte) 2);
        }

        board.setLift(2700);

        followPath(toBucket2);

        board.setBigGrab(false);
        board.setTinyGrab(false);
        sleep(500);

        board.setLift(0);
    }
}
