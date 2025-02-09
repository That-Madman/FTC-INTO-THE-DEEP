package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autoSystems.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class NetAuto extends WheelOp {
    Pose2D[] toRed = new Pose2D[] {
            new Pose2D(18, 0),
            new Pose2D(18, 0),
    };

    Pose2D[] toPink = new Pose2D[] {
            new Pose2D(18, 46),
            new Pose2D(18, 46),
    };

    Pose2D[] toOrange = new Pose2D[] {
            new Pose2D(8, 48),
            new Pose2D(8, 48),
    };
    Pose2D[] toYellow = new Pose2D[] {
        new Pose2D(8, 32),
        new Pose2D(8, 32),
    };
    Pose2D[] toBlue = new Pose2D[]{
        new Pose2D(20, 32),
        new Pose2D(20,32),
    };
    Pose2D[] toGrey = new Pose2D[]{

    };
    Pose2D[] toGreen = new Pose2D[]{

    };
    Pose2D[] toBrown = new Pose2D[]{

    };
    Pose2D[] toPurple = new Pose2D[]{

    };
    @Override
    public void onInit() {
        board.setBigGrab(true);
        board.setTinyGrab(true);
    }

    @Override
    public void run() {
        followPath(toRed);
        sleep(100);

        followPath(toPink);
        sleep(100);

        board.setLift(2700);
        board.setRot((byte) 2);
        sleep(500);

        followPath(toOrange);
        sleep(100);

        board.setBigGrab(false);
        board.setTinyGrab(false);
        sleep(1000);

        board.setRot((byte) 0);

        board.setLift(0);
        sleep(100);

        followPath(toYellow);
        sleep(50);
        followPath(toBlue);
        sleep(50);




        /*board.setRot((byte) 2);
        board.setLift(2700);

        followPath(toRed);

        board.setBigGrab(false);
        board.setTinyGrab(false);
        sleep(500);

        board.setLift(0);

        followPath(toPink);

        board.setReach((byte) 1);

        board.setPick(false);
        board.setSwivel((byte) 2);*/

        { //Block of Handoff
           /* board.setRot((byte) 0);
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

            board.setRot((byte) 2);/*
        }

        /*board.setLift(2700);

        followPath(toOrange);

        board.setBigGrab(false);
        board.setTinyGrab(false);
        sleep(500);

        board.setLift(0);*/


        }}}