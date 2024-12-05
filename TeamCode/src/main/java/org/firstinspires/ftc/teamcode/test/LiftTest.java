package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (group = "Tests")
public class LiftTest extends OpMode {
    private DcMotorEx v1, v2;

    @Override
    public void init () {
        v1 = hardwareMap.get(DcMotorEx.class, "v1");
        v2 = hardwareMap.get(DcMotorEx.class, "v2");

        v1.setDirection(DcMotorSimple.Direction.FORWARD);
        v2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop () {
        v1.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
        v2.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
    }
}
