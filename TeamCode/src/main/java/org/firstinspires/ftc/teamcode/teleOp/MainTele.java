package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;
import org.firstinspires.ftc.teamcode.extLib.hardware.Controller;

import Wheelie.PID;

@TeleOp
public class MainTele extends OpMode {
    private Board board;

    private Controller con1, con2;
    private double extentSlow = .5, extentMax = 1;
    private double extentPower = extentMax;

    private double armSlow = .25, armMax = 1;
    private double armPower = armMax;

    private PID exPID;
    private ElapsedTime elapsedTime;

    private boolean runningToTarget = false;

    // Button holding trackers
    private boolean a1Held;
    private boolean y1Held;

    public void init () {
        board = new Board(hardwareMap);
        con1 = new Controller(gamepad1);
        con2 = new Controller(gamepad2);

        exPID = new PID(1./500., 0, 0);
        exPID.capI(.1);
        elapsedTime = new ElapsedTime();
    }

    public void loop () {
        board.drive(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                (con1.bHeld) ? 0.5 : 1
        );

        extentPower = con1.bHeld ? extentSlow : extentMax;
        armPower = con2.upHeld ? armSlow : armMax;

        if(con1.aPressed){
            runningToTarget = true;
            elapsedTime.reset();
        }

        if(runningToTarget){
            board.powerExtent(exPID.pidCalc
                    (Math.abs(Board.netExt), Math.abs(board.getExtentPosition()), elapsedTime.seconds()));
        }

        if(!runningToTarget || con1.rightBumperHeld || con1.leftBumperHeld){
            board.powerExtent
                    (con1.rightBumperHeld ? extentPower : con1.leftBumperHeld ? -extentPower : 0);
            runningToTarget = false;
        }


        board.powerArm(armPower*(con2.rightTrigger - con2.leftTrigger));
        if(con2.aPressed)
            board.flipWrist();
        if(con2.bPressed)
            board.setClawPosition(board.getClawPosition() == 1 ? 0 : 1);

        con2.update();
        con1.update();

        // DEBUG TELEMETRY
        telemetry.addData("Extension Targeting", runningToTarget);
        telemetry.addData("Power", board.getExPower());
        telemetry.addData("Extension Position", board.getExtentPosition());
        telemetry.update();
    }
}
