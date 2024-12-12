package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class ManualHandOffTest extends OpMode {
    boolean bigClosed;
    boolean tinyClosed;
    boolean rotState;

    boolean bHeld;
    boolean yHeld;
    boolean upHeld;
    boolean leftHeld;
    boolean rightHeld;
    boolean downHeld;

    Servo tinyGrab;
    Servo bigGrab;
    Servo lRot;
    Servo rRot;
    Servo mRot;

    Servo r1, r2;
    byte re;

    Servo pick;
    Servo swivel;

    boolean closed;

    byte sState = 1;

    @Override
    public void init() {
        tinyGrab = hardwareMap.get(Servo.class, "tinyGrab");
        bigGrab = hardwareMap.get(Servo.class, "bigGrab");
        lRot = hardwareMap.get(Servo.class, "lRot");
        rRot = hardwareMap.get(Servo.class, "rRot");
        mRot = hardwareMap.get(Servo.class, "mRot");

        lRot.setDirection(Servo.Direction.REVERSE);
        setRot(false);
        setTinyGrab(false);
        setBigGrab(false);

        r1 = hardwareMap.get(Servo.class, "reachy");
        r2 = hardwareMap.get(Servo.class, "reachier");

        r2.setDirection(Servo.Direction.REVERSE);

        pick = hardwareMap.get(Servo.class, "pick");
        swivel = hardwareMap.get(Servo.class, "swivel");

        setPick(false);
        setSwivel(sState);
    }

    @Override
    public void loop() {
        if (!upHeld && gamepad1.dpad_up) {
            ++re;
            re %= 3;
        }

        setReach(re);

        upHeld = gamepad1.dpad_up;

        if (gamepad1.dpad_left && !leftHeld) {
            tinyClosed ^= true;
        }

        if (gamepad1.dpad_right && !rightHeld) {
            bigClosed ^= true;
        }

        if (gamepad1.dpad_down && !downHeld) {
            rotState ^= true;
        }

        setTinyGrab(tinyClosed);
        setBigGrab(bigClosed);
        setRot(rotState);

        leftHeld = gamepad1.dpad_left;
        rightHeld = gamepad1.dpad_right;
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
        bigGrab.setPosition(c ? 1 : 0.5);
    }

    void setRot (boolean u) {
        if (u) {
            lRot.setPosition(1);
            rRot.setPosition(1);
            mRot.setPosition(0);
        } else {
            lRot.setPosition(0);
            rRot.setPosition(0);
            mRot.setPosition(1);
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
