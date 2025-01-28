package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;
import org.firstinspires.ftc.teamcode.extLib.hardware.Controller;

@TeleOp
public class PickUpTest extends OpMode {
    private Board board;

    private Controller con1;
    private double extentSlow = .5, extentMax = 1;
    private double extentPower = extentMax;

    private double armSlow = .25, armMax = .5;
    private double armPower = armSlow;

    private double wristSlow = 1./10., wristMax = 1;
    private double wristSpeed = wristSlow;

    int target;
    private ElapsedTime elapsedTime;

    private boolean runningToTarget = false;

    // Button holding trackers
    private boolean a1Held;
    private boolean y1Held;

    public void init () {
        board = new Board(hardwareMap);
        con1 = new Controller(gamepad1);

        elapsedTime = new ElapsedTime();
        millis = System.currentTimeMillis();
    }

    long millis;
    public void loop () {
        if(con1.aPressed){
            runningToTarget = true;
            elapsedTime.reset();
        }

        if(con1.leftTriggerHeld || con1.rightTriggerHeld){
            board.powerArm(-armPower*(con1.rightTrigger - con1.leftTrigger));
            target = board.getArmPosition();
        }
        else
            board.powerArm(-1./100.*(target-board.getArmPosition()));

        if(con1.upHeld)
            board.setWristPosition(Range.clip(
                    board.getWristPosition() + ((System.currentTimeMillis()-millis)/1000.)*wristSpeed, 0, 1));
        else if(con1.downHeld)
            board.setWristPosition(Range.clip(
                    board.getWristPosition() - ((System.currentTimeMillis()-millis)/1000.)*wristSpeed, 0, 1));

        if(con1.bPressed)
            board.setClawPosition(board.getClawPosition() == 1 ? 0 : 1);

        con1.update();

        // DEBUG TELEMETRY
        telemetry.addData("Extension Targeting", runningToTarget);
        telemetry.addData("Power", board.getExPower());
        telemetry.addData("Extension Position", board.getExtentPosition());
        telemetry.addLine();
        telemetry.addData("Arm Position", board.getArmPosition());
        telemetry.addData("Wrist Position", board.getWristPosition());
        telemetry.addLine();

        double armAngle = (board.getArmPosition() * 360.0)/2688.5;
        telemetry.addData("Arm Angle (deg)", armAngle);

        telemetry.update();

        millis = System.currentTimeMillis();
    }
}
