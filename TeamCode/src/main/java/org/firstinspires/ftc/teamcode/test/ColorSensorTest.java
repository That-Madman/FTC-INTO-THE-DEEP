package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;


@TeleOp(group = "Tests")
public class ColorSensorTest extends OpMode {
    ColorRangeSensor sensor;

    @Override
    public void init() {
    sensor = hardwareMap.get(ColorRangeSensor.class, "Csensor");}
    @Override
    public void loop() {
        telemetry.addData("Red", sensor.red());
        telemetry.addData("Blue", sensor.blue());
        telemetry.addData("Green", sensor.green());
        if(sensor.blue()>(sensor.red()+sensor.green())){
            telemetry.addLine("I see blue");
        }
        else if(sensor.red()*1.5>(sensor.blue()+sensor.green())){
            telemetry.addLine("I see red");
        }
        else if((sensor.red()+sensor.green())/2>sensor.blue()*2) {
            telemetry.addLine("I see yellow");
        }
        else{
            telemetry.addLine("I do not see red, blue, nor yellow");
        }
    }
}
