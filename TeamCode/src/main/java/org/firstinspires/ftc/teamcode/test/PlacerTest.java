package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class PlacerTest extends OpMode {
    boolean bigClosed;
    boolean tinyClosed;
    boolean bHeld;
    boolean xHeld;

    Servo tinyGrab;
    Servo bigGrab;
    Servo lRot;
    Servo rRot;

    @Override
    public void init () {
        tinyGrab = hardwareMap.get(Servo.class, "tinyGrab");
        bigGrab = hardwareMap.get(Servo.class, "bigGrab");
        lRot = hardwareMap.get(Servo.class, "lRot");
        rRot = hardwareMap.get(Servo.class, "rRot");

        lRot.setDirection(Servo.Direction.REVERSE);
        setRot(false);
        setTinyGrab(false);
        setBigGrab(false);
    }

    @Override
    public void loop () {
        if (gamepad1.b && !bHeld) {
            tinyClosed ^= true;
        }

        if (gamepad1.x && !xHeld) {
            bigClosed ^= true;
        }

        setTinyGrab(tinyClosed);
        setBigGrab(bigClosed);
    }

    void setTinyGrab (boolean c) {
        tinyGrab.setPosition(c ? 1 : 0.25);
    }

    void setBigGrab (boolean c) {
        bigGrab.setPosition(c ? 1 : 0.25);
    }

    void setRot (boolean u) {
        if (u) {
            lRot.setPosition(1);
            rRot.setPosition(1);
        } else {
            lRot.setPosition(0);
            rRot.setPosition(0);
        }
    }
}
