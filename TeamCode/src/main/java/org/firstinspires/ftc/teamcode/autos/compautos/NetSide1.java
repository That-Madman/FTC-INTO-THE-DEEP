package org.firstinspires.ftc.teamcode.autos.compautos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.extLib.WheepOp;
import org.firstinspires.ftc.teamcode.extLib.hardware.Board;
import org.firstinspires.ftc.teamcode.extLib.hardware.Controller;

import Wheelie.PID;
import Wheelie.Pose2D;

@Autonomous
public class NetSide1 extends WheepOp {
        final private Pose2D[] toNet1 = new Pose2D[]{
            new Pose2D(0, 0, 0),
            new Pose2D(0, 10, 0),
    };

    final private Pose2D[] toNet2 = new Pose2D[] {
            new Pose2D(-21, 10, 0),
            new Pose2D(-21, 10, Math.toRadians(45))
    };

    final private Pose2D[] park1 = new Pose2D[] {
            new Pose2D(-21, 10, 0),
            new Pose2D(-21, 5, 0)
    };

    final private Pose2D[] park2 = new Pose2D[] {
            new Pose2D(-21, 5, 0),
            new Pose2D(-28, 5, 0)
    };

    private PID exPID;
    private ElapsedTime elapsedTime;

    @Override
    public void onInit() {
        board.setClawPosition(1.0);
    }

    @Override
    public void run() {
        followPath(toNet1);
        followPath(toNet2);
        //scoring preset sample
        board.setArmPosition(500); //change to up position when coded
        board.flipWrist();
        board.setClawPosition(0.0);
        while (Math.abs(Board.netExt - board.getExtentPosition()) > 20) {
            maintain();
            double power = exPID.pidCalc(Board.netExt, board.getExtentPosition());
            maintain();
            board.powerExtent(power);
        }

        sleep(2000);
        //now put it all back so it can move without falling over
        board.flipWrist();
        board.setClawPosition(1.0);
        board.setExtentTarget(0);
        board.setArmPosition(0); //change to down position when coded
        followPath(park1);
        followPath(park2);

        while(opModeIsActive()) {
            telemetry.addLine();
        }
    }
}