package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(group = "Test")
public class TestLift extends OpMode {
    private DcMotorEx spool = null;

    @Override
    public void init () {
        spool = hardwareMap.get(DcMotorEx.class, "spool");

        spool.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void loop () {
        spool.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

        telemetry.addData("Current tic count: ", spool.getCurrentPosition());
    }
}
