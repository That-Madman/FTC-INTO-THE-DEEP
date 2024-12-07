package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class ManualHandOffTest extends OpMode {
    Servo pick;
    Servo swivel;

    boolean closed;
    boolean bHeld;
    boolean yHeld;

    byte sState = 1;

    boolean bigClosed;
    boolean tinyClosed;
    boolean rotState;
    boolean xHeld;
    boolean aHeld;
    boolean dpadHeld;

    Servo tinyGrab;
    Servo bigGrab;
    Servo lRot;
    Servo rRot;

    Servo r1, r2;
    byte re;
    boolean downHeld;

    public void init () {
        pick = hardwareMap.get(Servo.class, "pick");
        swivel = hardwareMap.get(Servo.class, "swivel");

        setPick(false);
        setSwivel(sState);

        r1 = hardwareMap.get(Servo.class, "reachy");
        r2 = hardwareMap.get(Servo.class, "reachier");

        r2.setDirection(Servo.Direction.REVERSE);

        tinyGrab = hardwareMap.get(Servo.class, "tinyGrab");
        bigGrab = hardwareMap.get(Servo.class, "bigGrab");
        lRot = hardwareMap.get(Servo.class, "lRot");
        rRot = hardwareMap.get(Servo.class, "rRot");

        lRot.setDirection(Servo.Direction.REVERSE);
        setRot(false);
        setTinyGrab(false);
        setBigGrab(false);
    }

    public void loop () {
        if (gamepad1.a && !aHeld) {
            tinyClosed ^= true;
        }

        if (gamepad1.x && !xHeld) {
            bigClosed ^= true;
        }

        if (gamepad1.dpad_up && !dpadHeld) {
            rotState ^= true;
        }

        setTinyGrab(tinyClosed);
        setBigGrab(bigClosed);
        setRot(rotState);

        aHeld = gamepad1.a;
        xHeld = gamepad1.x;
        dpadHeld = gamepad1.dpad_up;

        if (!downHeld && gamepad1.dpad_down) {
            ++re;
            re %= 3;
        }

        setReach(re);

        downHeld = gamepad1.dpad_down;

        if (!bHeld && gamepad1.b) {
            closed ^= true;
        }
        if (!yHeld && gamepad1.y) {
            ++sState;
            sState %= 3;
        }

        setPick(closed);
        setSwivel(sState);

        bHeld = gamepad1.b;
        yHeld = gamepad1.y;
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

    void setPick (boolean c) {
        pick.setPosition(c ? 0.5 : 1);
    }

    void setSwivel (byte s) {
        switch (s) {
            case 0:
                swivel.setPosition(0);
                break;
            case 1:
                swivel.setPosition(0.5);
                break;
            case 2:
                swivel.setPosition(1);
                break;
        }
    }
}
