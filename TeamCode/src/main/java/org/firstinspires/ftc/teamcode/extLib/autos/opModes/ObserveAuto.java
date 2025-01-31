package org.firstinspires.ftc.teamcode.extLib.autos.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.extLib.autos.systems.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class ObserveAuto extends WheelOp {

    final private Pose2D[] forward = new Pose2D[] {
            new Pose2D(0,0),
            new Pose2D(19, 8)
    };

    final private Pose2D[] backup = new Pose2D[] {
            new Pose2D(19, 4),
            new Pose2D(3, 0)
    };

    final private Pose2D[] readyPush1 = new Pose2D[] {
            new Pose2D(3, 0),
            new Pose2D(3, -27),
            new Pose2D(47, -27)
    };

    final private Pose2D[] readyPush2 = new Pose2D[] {
            new Pose2D(47,-27),
            new Pose2D(50,-37)
    };

    final private Pose2D[] push1 = new Pose2D[] {
            new Pose2D(50, -35),
            new Pose2D(3, -35)
    };

    final private Pose2D[] readyPush3 = new Pose2D[] {
            new Pose2D(0, -35),
            new Pose2D(25, -35),
            new Pose2D(50, -35)
    };

    final private Pose2D[] push2 = new Pose2D[] {
            new Pose2D(50, -35),
            new Pose2D(50, -42),
            new Pose2D(3, -42)
    };

    final private Pose2D[] readyStrafeToScore1 = new Pose2D[] {
            new Pose2D(6, -42),
            new Pose2D(6, -28)
    };

    final private Pose2D[] strafeToScore1 = new Pose2D [] {
            new Pose2D(5, -28),
            new Pose2D(1, -28)
    };

    final private Pose2D[] toSubmersible = new Pose2D[] {
            new Pose2D(6, -28),
            new Pose2D(6, 4),
            new Pose2D(19, 4)
    };

    @Override
    public void onInit() {
        board.setBigGrab(true);
        board.setTinyGrab(true);
    }

    @Override
    public void run() {
        board.setRot((byte) 4);
        board.setLift(850);

       followPath(forward);
        moveUntilTouch();

        board.setRot((byte) 3);
        sleep(1000);

        board.setTinyGrab(false);
        board.setBigGrab(false);

        board.setLift(0);
        board.drive(0,0,0);

        followPath(backup);

        followPath(readyPush1);
        followPath(readyPush2);

        followPath(push1);

        followPath(readyPush3);

        followPath(push2);

        followPath(readyStrafeToScore1);

        board.setRot((byte) 1);

        followPath(strafeToScore1);

        board.setTinyGrab(true);
        board.setBigGrab(true);

        sleep(250);

        board.setRot((byte) 4);

        sleep(250);

        board.setLift(850);

        followPath(toSubmersible);
        moveUntilTouch();

        board.setRot((byte) 3);

        board.setTinyGrab(false);
        board.setBigGrab(false);

        sleep(500);

        board.setLift(0);
        board.drive(0, 0, 0);
    }
}