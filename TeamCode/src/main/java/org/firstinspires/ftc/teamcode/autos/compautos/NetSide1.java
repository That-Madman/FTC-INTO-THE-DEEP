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
            new Pose2D(-15.5, 1, 0),
    };

    //make arm slower
    //have the arm go down when it reaches position for +3
    //more time between wrist flip and droping..maybe 0.5 sec
    //strafe missed for some reason :'(
    //make it a bit slower??
    //just gets stuck sometimes
    //goes forward into the net??
    //ran straight into wall once

    final private Pose2D[] park1 = new Pose2D[] {
            new Pose2D(-15.5, 0, 0),
            new Pose2D(-6, 0, 0),
            new Pose2D(-6, 50, 0),
            new Pose2D(14, 50, 0)
    };

    final private Pose2D[] park2 = new Pose2D[] {
            new Pose2D(-21, 5, 0),
            new Pose2D(-28, 5, 0)
    };

    private ElapsedTime timer;
    private PID liftPID = new PID(-1./750., 0, 0);

    @Override
    public void onInit() {
        board.setClawPosition(0);
        board.setWristPosition(1);
        liftPID.capI(0.25);
    }

    @Override
    public void run() {
        followPath(toNet1, new Pose2D(-15.5, 1));
        //followPath(rotate);
        //scoring preset sample

        timer = new ElapsedTime();
        while (timer.seconds() < 7.5 && opModeIsActive()&&(Math.abs(Board.netExt - board.getExtentPosition()) > 50 ||
                Math.abs(board.getArmPosition() - 920) > 20)) {
            board.powerArm(-1./750.*(920-board.getArmPosition()));
            if(Math.abs(board.getArmPosition()) >= 460){
                board.powerExtent((Board.netExt - board.getExtentPosition())*1./250.);
            }
           // maintain();

            telemetry.addData("Arm", board.getArmPosition());
            telemetry.addData("Extent", board.getExtentPosition());
            telemetry.update();
        }

        board.setWristPosition(0);

        sleep(1500);
        board.setClawPosition(1);
        sleep(1500);
        board.setWristPosition(1);
        sleep(1500);

        timer = new ElapsedTime();
        while (timer.seconds() < 5 &&opModeIsActive()&&(Math.abs(board.getExtentPosition()) > 50 ||
                Math.abs(board.getArmPosition() - 100) > 20)) {
            board.powerArm(-1./750.*(100 -board.getArmPosition()));
            board.powerExtent(-board.getExtentPosition()*1./100.);
            //maintain();

            telemetry.addData("Arm", board.getArmPosition());
            telemetry.addData("Extent", board.getExtentPosition());
            telemetry.update();
        }
        board.setWristPosition(0);
        followPath(park1);
        board.powerArm(.25);
        sleep(500);
        board.powerArm(0);
        while(opModeIsActive()) {
            telemetry.addLine("complete");
            //maintain();

            telemetry.addData("Arm", board.getArmPosition());
            telemetry.addData("Extent", board.getExtentPosition());
            telemetry.update();
        }
    }
}