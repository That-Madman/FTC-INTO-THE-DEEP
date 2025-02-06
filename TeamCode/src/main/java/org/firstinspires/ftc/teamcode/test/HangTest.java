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
    }

    @Override
    public void loop() {
        s1.setPower((gamepad1.left_bumper ? 1 : 0) - (gamepad1.share ? 1 : 0));
        s2.setPower((gamepad1.right_bumper ? 1 : 0) - (gamepad1.start ? 1 : 0));
    }
}
