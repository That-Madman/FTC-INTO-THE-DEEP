package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (group = "Tests")
public class TestMotors extends OpMode {
    private final DcMotor[] base = {null, null, null, null};

    boolean upHeld, downHeld;

    byte index = 0;

    @Override
    public void init () {
        base[0] = hardwareMap.get(DcMotor.class, "fl");
        base[1] = hardwareMap.get(DcMotor.class, "fr");
        base[2] = hardwareMap.get(DcMotor.class, "br");
        base[3] = hardwareMap.get(DcMotor.class, "bl");

        base[0].setDirection(DcMotorSimple.Direction.REVERSE);
        base[1].setDirection(DcMotorSimple.Direction.REVERSE);
        base[2].setDirection(DcMotorSimple.Direction.FORWARD);
        base[3].setDirection(DcMotorSimple.Direction.REVERSE);

        base[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[1].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[2].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[3].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop () {
        base[index].setPower(-gamepad1.left_stick_y);

        if (gamepad1.dpad_up && !upHeld) {
            ++index;
            index %= 4;
        } else if (gamepad2.dpad_down && !downHeld) {
            --index;
            if (-1 == index) {
                index = 3;
            }
        }

        telemetry.addData("Currently controlling",
                new String[] {
                        "front left",
                        "front right",
                        "back right",
                        "back left"
                } [index]);

        upHeld = gamepad1.dpad_up;
        downHeld = gamepad1.dpad_down;
    }
}
