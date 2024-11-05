package org.firstinspires.ftc.teamcode.Auto;

import static java.lang.Math.toRadians;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SpoolStates;
import org.firstinspires.ftc.teamcode.WheelOp;

import Wheelie.Pose2D;

@Autonomous
public class Left extends WheelOp {
    final private Pose2D[] path1 = new Pose2D [] {
            new Pose2D (10,0),
            new Pose2D (10, -18, toRadians(25)),
            new Pose2D (10, -36, toRadians(45))
    };

    final private Pose2D[] path2 = new Pose2D [] {
            new Pose2D(5,-37, toRadians(45)),
            new Pose2D(5, -37, toRadians(45))
    };

    final private Pose2D[] path3 = new Pose2D[] {
            new Pose2D (10, -36, toRadians(45)),
            new Pose2D (10, -20, toRadians(35)),
            new Pose2D (20, -20, 0),
            new Pose2D (30, -15, toRadians(270))
    };

    final private Pose2D[] path4 = new Pose2D[] {
            new Pose2D(30, -10, toRadians(270)),
            new Pose2D(30, -10, toRadians(270))
    };

    @Override
    public void run () {
        board.autoSpoolOverride();
        board.setSweep(0.5);
        board.setSpoolPos(SpoolStates.LIFTEDABIT);

        followLoop(path1, 5);

        board.spoolTo(SpoolStates.BUCKETHIGH);

        followLoop(path2, 5);

        board.setSweep(-0.5);
        sleep(1000);
        board.setSweep(0);

        followLoop(path3, 5);

        board.setSpoolPos(SpoolStates.CHAMBERLOW + 100);

        followLoop(path4, 5);
    }
}
