package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Wheelie.PID;

@TeleOp (group = "Tests")
public class LiftPidTest extends OpMode {
    private DcMotorEx v1, v2;

    private int height;

    public void init () {
        v1 = hardwareMap.get(DcMotorEx.class, "v1");
        v2 = hardwareMap.get(DcMotorEx.class, "v2");

        v1.setDirection(DcMotorSimple.Direction.REVERSE);
        v2.setDirection(DcMotorSimple.Direction.FORWARD);

        v1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v1.setPower(1);
        v1.setTargetPosition(0);
        v1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        v2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v2.setPower(1);
        v2.setTargetPosition(0);
        v2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void loop () {
        height += ((gamepad1.dpad_up ? 1 : 0) - (gamepad1.dpad_down ? 1 : 0)) * 100;

        height = Math.max(height, 0);
        height = Math.min (height, 2800);

        v1.setTargetPosition(height);
        v2.setTargetPosition(height);

        telemetry.addData("target is", height);
        telemetry.addData("v1 curr Pos is", v1.getCurrentPosition());
        telemetry.addData("v2 curr Pos is", v2.getCurrentPosition());
    }
}
