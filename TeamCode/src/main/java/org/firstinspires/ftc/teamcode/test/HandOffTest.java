package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class HandOffTest extends LinearOpMode {
    Servo pick;
    Servo swivel;

    boolean bHeld;

    Servo tinyGrab;
    Servo bigGrab;
    Servo lRot;
    Servo rRot;

    @Override
    public void runOpMode () {
        pick = hardwareMap.get(Servo.class, "pick");
        swivel = hardwareMap.get(Servo.class, "swivel");

        setPick(false);
        setSwivel((byte) 1);

        tinyGrab = hardwareMap.get(Servo.class, "tinyGrab");
        bigGrab = hardwareMap.get(Servo.class, "bigGrab");
        lRot = hardwareMap.get(Servo.class, "lRot");
        rRot = hardwareMap.get(Servo.class, "rRot");

        lRot.setDirection(Servo.Direction.REVERSE);
        setRot(false);
        setTinyGrab(false);
        setBigGrab(false);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            if (!bHeld && gamepad1.b) {
                setPick(true);

                sleep(500);

                setSwivel((byte) 0);

                sleep(500);

                setTinyGrab(true);

                sleep(500);

                setPick(false);

                sleep(500);

                setBigGrab(true);

                sleep(500);

                setRot(true);
            }

            bHeld = gamepad1.b;
        }
    }

    void setPick (boolean c) {
        pick.setPosition(c ? 1 : 0.25);
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
