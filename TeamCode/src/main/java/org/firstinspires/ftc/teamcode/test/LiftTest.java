package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (group = "Tests")
public class LiftTest extends OpMode {
    private DcMotorEx v1, v2;

    @Override
    public void init () {
        v1 = hardwareMap.get(DcMotorEx.class, "v1");
        v2 = hardwareMap.get(DcMotorEx.class, "v2");

        v2.setDirection(DcMotorSimple.Direction.FORWARD);
        v1.setDirection(DcMotorSimple.Direction.REVERSE);

        v1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        v2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        v1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        v2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop () {
        v1.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
        v2.setPower(gamepad1.left_trigger - gamepad1.right_trigger);

        telemetry.addData("v1 tic", v1.getCurrentPosition());
        telemetry.addData("v2 tic", v2.getCurrentPosition());
    }
}
