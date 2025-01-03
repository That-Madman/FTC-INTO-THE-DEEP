package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class HandOffTest extends OpMode {
    boolean closed;

    boolean aHeld;
    boolean bHeld;
    boolean xHeld;
    boolean yHeld;

    byte sState = 1;
    byte re;

    Servo tinyGrab;
    Servo bigGrab;
    Servo lRot;
    Servo rRot;
    Servo mRot;

    Servo r1, r2;

    Servo pick;
    Servo swivel;

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

        r1 = hardwareMap.get(Servo.class, "reachy");
        r2 = hardwareMap.get(Servo.class, "reachier");

        r2.setDirection(Servo.Direction.REVERSE);

        pick = hardwareMap.get(Servo.class, "pick");
        swivel = hardwareMap.get(Servo.class, "swivel");

        setPick(false);
        setSwivel(sState);
    }

    @Override
    public void loop () {
            if (!aHeld && gamepad1.a) {
                closed ^= true;
            }

            if (!bHeld && gamepad1.b) {
                setBigGrab(false);
                setTinyGrab(false);

                setPick(false);
                sleep(100);

                setReach((byte) 1);
                sleep(100);

                setSwivel((byte) 0);
                sleep(100);

                setReach((byte) 0);
                sleep(100);

                setTinyGrab(true);
                sleep(100);

                setPick(true);
                sleep(100);

                setReach((byte) 1);
                sleep(100);

                setBigGrab(true);
                sleep(100);

                setRot(true);
                sleep (1100);


                re = 1;
                sState = 1;
                closed = true;
            }

            if (!yHeld && gamepad1.y) {
                ++re;
                re %= 3;
            }


            if (!xHeld && gamepad1.x) {
                ++sState;
                sState %= 3;
            }

            setReach(re);
            setSwivel(sState);
            setPick(closed);

            telemetry.addData("a?", gamepad1.a);
            telemetry.addData("a held?", aHeld);
            telemetry.addData("closed?", closed);

            aHeld = gamepad1.a;
            bHeld = gamepad1.b;
            xHeld = gamepad1.x;
            yHeld = gamepad1.y;

            if (gamepad1.dpad_up) {
                setBigGrab(false);
                setTinyGrab(false);
                setRot(false);
            }
        }

    void setTinyGrab (boolean c) {
        tinyGrab.setPosition(c ? 0.25 : 1);
    }

    void setBigGrab (boolean c) {
        bigGrab.setPosition(c ? 1 : 0.5);
    }

    void setRot (boolean u) {
        if (u) {
            lRot.setPosition(1);
            rRot.setPosition(1);
            mRot.setPosition(1);
        } else {
            lRot.setPosition(0.1);
            rRot.setPosition(0.1);
            mRot.setPosition(0);
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
                swivel.setPosition(0.2);
                break;
            case 1:
                swivel.setPosition(0.5);
                break;
            case 2:
                swivel.setPosition(1);
                break;
        }
    }

    public void sleep (long millis) {
        resetRuntime();
        while (getRuntime() < (double) millis / 1000);
    }

}