package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class PlacerTest extends OpMode {
    boolean bigClosed;
    boolean tinyClosed;
    boolean rotState;
    boolean mRotState;

    boolean bHeld;
    boolean xHeld;
    boolean yHeld;
    boolean aHeld;

    Servo tinyGrab;
    Servo bigGrab;
    Servo lRot;
    Servo rRot;
    Servo mRot;

    @Override
    public void init () {
        tinyGrab = hardwareMap.get(Servo.class, "tinyGrab");
        bigGrab = hardwareMap.get(Servo.class, "bigGrab");
        lRot = hardwareMap.get(Servo.class, "lRot");
        rRot = hardwareMap.get(Servo.class, "rRot");
        mRot = hardwareMap.get(Servo.class, "mRot");

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

        if (gamepad1.y && !yHeld) {
            rotState ^= true;
        }

        if (gamepad1.a && !aHeld) {
            mRotState ^= true;
        }

        setTinyGrab(tinyClosed);
        setBigGrab(bigClosed);
        setRot(rotState);

        bHeld = gamepad1.b;
        xHeld = gamepad1.x;
        yHeld = gamepad1.y;
        aHeld = gamepad1.a;
    }

    void setTinyGrab (boolean c) {
        tinyGrab.setPosition(c ? 1 : 0.25);
    }

    void setBigGrab (boolean c) {
        bigGrab.setPosition(c ? 1 : 0.5);
    }

    void setRot (boolean u) {
        if (u) {
            lRot.setPosition(0);
            rRot.setPosition(0);
        } else {
            lRot.setPosition(1);
            rRot.setPosition(1);
        }
    }
}
