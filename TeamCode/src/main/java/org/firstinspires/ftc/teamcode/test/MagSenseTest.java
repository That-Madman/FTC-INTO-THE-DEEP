package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp (group = "Tests")
public class MagSenseTest extends OpMode {
    private TouchSensor v1t, v2t;

    @Override
    public void init() {
        v1t = hardwareMap.get(TouchSensor.class, "v1t");
        v2t = hardwareMap.get(TouchSensor.class, "v2t");
    }

    @Override
    public void loop() {
        telemetry.addData("v1t", v1t.isPressed());
        telemetry.addData("v2t", v2t.isPressed());
    }
}
