package org.firstinspires.ftc.teamcode.test;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp (group = "Tests")
public class SubmersibleSensorTest extends OpMode {
    TouchSensor t1, t2;
    LED feedback;

    @Override
    public void init() {
        t1 = hardwareMap.get(TouchSensor.class, "t1");
        t2 = hardwareMap.get(TouchSensor.class, "t2");
        feedback = hardwareMap.get(LED.class, "led");

        feedback.off();
    }

    @Override
    public void loop() {
        if (t1.isPressed() && t2.isPressed()) {
            feedback.on();
            telemetry.addData("Touching Submersible", true);
        } else {
            feedback.off();
            telemetry.addData("Touching Submersible", false);
        }

        telemetry.addData("T1", t1.isPressed());
        telemetry.addData("T2", t2.isPressed());
    }
}
