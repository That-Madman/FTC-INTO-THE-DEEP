package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class ReachTest extends OpMode {
    Servo reach;
    boolean re;
    boolean bHeld;

    @Override
    public void init () {
        reach = hardwareMap.get(Servo.class, "reach");
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
        reach.setPosition(r ? 1 : 0);
    }
}
