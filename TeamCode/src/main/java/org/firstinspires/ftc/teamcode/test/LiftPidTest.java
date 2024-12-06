package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Wheelie.PID;

@TeleOp (group = "Tests")
public class LiftPidTest extends OpMode {
    private DcMotorEx v1, v2;
    private final PID v1p = new PID (0.1, 0, 0);
    private final PID v2p = new PID (0.1, 0, 0);

    private int height;

    public void init () {
        v1 = hardwareMap.get(DcMotorEx.class, "v1");
        v2 = hardwareMap.get(DcMotorEx.class, "v2");

        v1.setDirection(DcMotorSimple.Direction.FORWARD);
        v2.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void loop () {
        height += (int) (gamepad1.left_trigger - gamepad1.right_trigger * 100);

        height = Math.min(height, 0);

        v1.setPower(v1p.pidCalc(height, v1.getCurrentPosition()));
        v2.setPower(v2p.pidCalc(height, v2.getCurrentPosition()));
    }
}
