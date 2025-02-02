package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;
import org.firstinspires.ftc.teamcode.extLib.hardware.Controller;

@TeleOp
public class MainTele extends OpMode {
    private Board board;

    private Controller con1, con2;
    private double extentSlow = .5, extentMax = 1;
    private double extentPower = extentMax;

    private double armSlow = .25, armMax = .5;
    private double armPower = armMax;

    private double wristSlow = 1./10., wristMax = 1;
    private double wristSpeed = wristMax;

    int brakeTarget;
    final int pickUpTicks = 60; final double pickUpWristPos = .0267;
    private ElapsedTime elapsedTime;

    private boolean extRunningToTarget = false,
            armRunningToTarget = false;

    // Button holding trackers
    private boolean a1Held;
    private boolean y1Held;

    public void init () {
        board = new Board(hardwareMap);
        con1 = new Controller(gamepad1);
        con2 = new Controller(gamepad2);

        elapsedTime = new ElapsedTime();
        millis = System.currentTimeMillis();
    }

    long millis;
    public void loop () {
        board.drive(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                (con1.leftTriggerHeld) ? 0.25 : 1
        );

        extentPower = con1.leftTriggerHeld ? extentSlow : extentMax;
        armPower = con2.rightBumperHeld ? armSlow : armMax;
        wristSpeed = con2.rightBumperHeld ? wristSlow : wristMax;

        if(con1.aPressed){
            extRunningToTarget = true;
            elapsedTime.reset();
        }

        if(extRunningToTarget){
            board.powerExtent((Math.abs(Board.netExt)- Math.abs(board.getExtentPosition()))*1./500.);
        }

        if(!extRunningToTarget || con1.rightBumperHeld || con1.leftBumperHeld){
            board.powerExtent
                    (con1.rightBumperHeld ? extentPower : con1.leftBumperHeld ? -extentPower : 0);
            extRunningToTarget = false;
        }

        if(con2.aPressed){
            board.setWristPosition(pickUpWristPos);
            armRunningToTarget = true;
        }
        if(armRunningToTarget){
            board.powerArm(-1./100.*(pickUpTicks -board.getArmPosition()));
        }

        if(con2.leftTriggerHeld || con2.rightTriggerHeld){
            board.powerArm(-armPower*(con2.rightTrigger - con2.leftTrigger));
            brakeTarget = board.getArmPosition();
            armRunningToTarget = false;
        }
        else if(!armRunningToTarget)
            board.powerArm(-1./100.*(brakeTarget -board.getArmPosition()));

        if(con2.downHeld)
            board.setWristPosition(Range.clip(
                    board.getWristPosition() + ((System.currentTimeMillis()-millis)/1000.)*wristSpeed, 0, 1));
        else if(con2.upHeld)
            board.setWristPosition(Range.clip(
                    board.getWristPosition() - ((System.currentTimeMillis()-millis)/1000.)*wristSpeed, 0, 1));

        if(con2.bPressed)
            board.setClawPosition(board.getClawPosition() == 1 ? 0 : 1);

        con2.update();
        con1.update();

        // DEBUG TELEMETRY
        telemetry.addData("Extension Targeting", extRunningToTarget);
        telemetry.addData("Power", board.getExPower());
        telemetry.addData("Extension Position", board.getExtentPosition());
        telemetry.addLine();
        telemetry.addData("Arm Position", board.getArmPosition());
        telemetry.addData("Wrist Position", board.getWristPosition());
        telemetry.update();

        millis = System.currentTimeMillis();
    }
}
