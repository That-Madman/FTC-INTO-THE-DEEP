package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class ReachTest extends OpMode {
    Servo r1, r2;
    byte re;
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
            ++re;
            re %= 3;
        }

        setReach(re);

        bHeld = gamepad1.b;
    }

    public void setReach (byte r) {
        switch (r) {
            case 0:
                r1.setPosition(1);
                r2.setPosition(1);
                break;

            case 1:
                r1.setPosition(0.5);
                r2.setPosition(0.5);
                break;

            case 2:
                r1.setPosition(0.1);
                r2.setPosition(0.1);
                break;
        }
    }
}
