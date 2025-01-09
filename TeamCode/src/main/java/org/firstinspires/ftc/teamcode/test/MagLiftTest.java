package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp (group = "Tests")
public class MagLiftTest extends OpMode {
    private DcMotorEx v1, v2;

    private TouchSensor v1t, v2t;

    private int height;

    @Override
    public void init() {
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

        v1t = hardwareMap.get(TouchSensor.class, "v1t");
        v2t = hardwareMap.get(TouchSensor.class, "v2t");
    }

    @Override
    public void loop() {
        height += ((gamepad1.dpad_up ? 1 : 0) - (gamepad1.dpad_down ? 1 : 0)) * 100;

        height = Math.max(height, 0);
        height = Math.min(height, 2800);

        v1.setTargetPosition(height);
        v2.setTargetPosition(height);

        if (v1t.isPressed()) {
            v1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            v1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (0 == height) {
                setLiftDampen(0);
                telemetry.addLine("v1 sleeping");
            } else {
                setLiftDampen(0.7f);
            }
        }

        if(v2t.isPressed()) {
            v2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            v2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (0 == height) {
            setLiftDampen(0);
            telemetry.addLine("v2 sleeping");
        } else {
            setLiftDampen(0.7f);
        }
    }

        telemetry.addData("target is", height);

        telemetry.addData("v1 curr Pos is",v1.getCurrentPosition());
        telemetry.addData("v2 curr Pos is",v2.getCurrentPosition());

        telemetry.addData("v1t is", v1t.isPressed());
        telemetry.addData("v2t is", v2t.isPressed());

        telemetry.addData("v1 power is", v1.getPower());
        telemetry.addData("v2 power is", v2.getPower());
    }

    public void setLiftDampen (float damp) {
        v1.setPower(damp);
        v2.setPower(damp);
    }
}
