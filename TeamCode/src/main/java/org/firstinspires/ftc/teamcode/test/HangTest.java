package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp (group = "Tests")
public class HangTest extends OpMode {
    CRServo s1, s2;

    @Override
    public void init() {
        s1 = hardwareMap.get(CRServo.class, "s1");
        s2 = hardwareMap.get(CRServo.class, "s2");

        s1.setDirection(CRServo.Direction.REVERSE);
    }

    @Override
    public void loop() {
        s1.setPower(gamepad1.left_trigger - gamepad1.right_stick_x);
        s2.setPower(gamepad1.left_trigger - gamepad1.right_stick_x);
    }
}
