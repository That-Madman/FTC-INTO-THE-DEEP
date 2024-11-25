package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class TestPickAndSwivel extends OpMode {
    Servo pick;
    Servo swivel;

    boolean closed;
    boolean bHeld;
    boolean yHeld;

    byte sState = 1;

    @Override
    public void init () {
        pick = hardwareMap.get(Servo.class, "pick");
        swivel = hardwareMap.get(Servo.class, "swivel");

        setPick(false);
        setSwivel(sState);
    }

    @Override
    public void loop () {
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
}
