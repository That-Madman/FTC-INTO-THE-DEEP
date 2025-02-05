package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp (group = "Tests")
public class RamTest extends OpMode {
    private final DcMotor[] base = {null, null, null, null};

    @Override
    public void init() {
        base[0] = hardwareMap.get(DcMotor.class, "fl");
        base[1] = hardwareMap.get(DcMotor.class, "fr");
        base[2] = hardwareMap.get(DcMotor.class, "br");
        base[3] = hardwareMap.get(DcMotor.class, "bl");

        base[0].setDirection(DcMotorSimple.Direction.REVERSE);
        base[1].setDirection(DcMotorSimple.Direction.FORWARD);
        base[2].setDirection(DcMotorSimple.Direction.REVERSE);
        base[3].setDirection(DcMotorSimple.Direction.REVERSE);

        base[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[1].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[2].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[3].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        base[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        base[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        base[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        base[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            base[0].setPower(1);
            base[1].setPower(1);
            base[2].setPower(1);
            base[3].setPower(1);
        } else {
            base[0].setPower(0);
            base[1].setPower(0);
            base[2].setPower(0);
            base[3].setPower(0);
        }
    }
}
