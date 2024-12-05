/*package org.firstinspires.ftc.teamcode.extLib;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class UseSparkFunOTOS extends OpMode {
    SparkFunOTOS sparkFunOTOS;

    public void init() {
        sparkFunOTOS = hardwareMap.get(SparkFunOTOS.class, "otos");
        configureOTOS();
    }

    private void configureOTOS(){
        sparkFunOTOS.setLinearUnit(DistanceUnit.INCH);
        sparkFunOTOS.setAngularUnit(AngleUnit.DEGREES);
        sparkFunOTOS.setOffset(new SparkFunOTOS.Pose2D(0, 0, 0));
        sparkFunOTOS.setLinearScalar(1.0);
        sparkFunOTOS.setAngularScalar(1.0);
        sparkFunOTOS.resetTracking();
        sparkFunOTOS.setPosition(new SparkFunOTOS.Pose2D(0,0,0));
        sparkFunOTOS.calibrateImu(255, false);
    }

    @Override
    public void loop() {
        SparkFunOTOS.Pose2D pos = sparkfunOTOS.getPosition();
        telemetry.addData("X (inch)", pos.x);
        telemetry.addData("Y (inch)", pos.y);
        telemetry.addData("Heading (degrees)", pos.h);
    }
}
*/