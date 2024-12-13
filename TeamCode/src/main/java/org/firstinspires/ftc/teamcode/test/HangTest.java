package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class HangTest extends OpMode {
    Servo lhs, rhs;
    DcMotor lhm, rhm;

    @Override
    public void init () {
        lhs = hardwareMap.get(Servo.class, "lhs");
        rhs = hardwareMap.get(Servo.class, "lhs");

        lhm = hardwareMap.get(DcMotor.class, "lhs");
        rhm = hardwareMap.get(DcMotor.class, "rhs");
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_left) {
            lhs.setPosition(1);
            rhs.setPosition(1);
        } else if (gamepad1.dpad_right) {
            lhs.setPosition(0);
            rhs.setPosition(0);
        }

        if (gamepad1.dpad_up) {
            lhm.setPower(1);
            rhm.setPower(1);
        } else if (gamepad1.dpad_down) {
            lhm.setPower(-1);
            rhm.setPower(-1);
        } else  {
            lhm.setPower(0);
            rhm.setPower(0);
        }
    }
}
