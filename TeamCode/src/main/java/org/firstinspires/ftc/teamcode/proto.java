package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class proto extends OpMode {

    CRServo grips [] = {null, null};

    @Override
    public void init () {
        grips[0] = hardwareMap.get(CRServo.class, "s1");
        grips[1] = hardwareMap.get(CRServo.class, "s2");

        grips[1].setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop () {
        if (gamepad1.a) {
           grips[0].setPower(1);
           grips[1].setPower(1);
        } else if (gamepad1.b) {
            grips[0].setPower(-1);
            grips[1].setPower(-1);
        } else {
            grips[0].setPower(0);
            grips[1].setPower(0);
        }
    }
}
