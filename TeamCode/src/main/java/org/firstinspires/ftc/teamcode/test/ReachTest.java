package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class ReachTest extends OpMode {
    Servo r1, r2;
    boolean re;
    boolean bHeld;

    @Override
    public void init () {
        r1 = hardwareMap.get(Servo.class, "reachy");
        r2 = hardwareMap.get(Servo.class, "reachier");

        r2.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void loop () {
        if (!bHeld && gamepad1.b) {
            re ^= true;
        }

        setReach(re);

        bHeld = gamepad1.b;
    }

    public void setReach (boolean r) {
        if (r) {
            r1.setPosition(1);
            r2.setPosition(1);
        } else {
            r1.setPosition(0);
            r2.setPosition(0);
        }
    }
}
