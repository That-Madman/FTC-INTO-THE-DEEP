package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp (group = "Tests")
public class SubmersibleSenseTest extends OpMode {
    TouchSensor t1, t2;

    @Override
    public void init () {
        t1 = hardwareMap.get(TouchSensor.class, "t1");

        t2 = hardwareMap.get(TouchSensor.class, "t2");
    }

    @Override
    public void loop() {
        telemetry.addData("t1", t1.isPressed());
        telemetry.addData("t2", t2.isPressed());
    }
}
