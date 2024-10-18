package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "Test")
public class ServoTest extends OpMode {
    boolean clawOpen = false, bHeld = false;
    Servo servo = null;

    public void init () {
       servo = hardwareMap.get(Servo.class, "gripper");
    }

    public void loop () {
        if (gamepad2.b && !bHeld) {
            clawOpen = !clawOpen;
        }

        setClaw(clawOpen);

        bHeld = gamepad2.b;

    }

    public void setClaw (boolean open) {
        if (open) {
            servo.setPosition(0); //open position, according to Caleb
        } else {
            servo.setPosition(1); //thus follows that this is the closed position
        }
    }
}
