package org.firstinspires.ftc.teamcode.extLib;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class UseSparkFunOTOS extends OpMode {
    SparkFunOTOS sparkFunOTOS;
    Board board;

    public void init() {
        sparkFunOTOS = hardwareMap.get(SparkFunOTOS.class, "otos");
        board = new Board(hardwareMap);
        configureOTOS();
    }

    private void configureOTOS(){
        sparkFunOTOS.setLinearUnit(DistanceUnit.INCH);
        sparkFunOTOS.setAngularUnit(AngleUnit.RADIANS);
        sparkFunOTOS.setOffset(new SparkFunOTOS.Pose2D(3.5, 6, 0));
        sparkFunOTOS.setLinearScalar(100./92.5117);
        sparkFunOTOS.setAngularScalar(Math.toRadians(2160.0)/Math.toRadians(2175.0));
        sparkFunOTOS.resetTracking();
        sparkFunOTOS.setPosition(new SparkFunOTOS.Pose2D(0,0,0));
        sparkFunOTOS.calibrateImu(255, false);
    }

    @Override
    public void loop() {
//        board.setPowers(gamepad1.right_stick_y/2.0, gamepad1.right_stick_y/2.0, gamepad2.right_stick_y/2.0, gamepad2.right_stick_y/2.0);
        board.drive(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x);

        SparkFunOTOS.Pose2D pos = sparkFunOTOS.getPosition();
        telemetry.addData("X (inch)", pos.x);
        telemetry.addData("Y (inch)", pos.y);
        telemetry.addData("Heading (degrees)", pos.h);
        telemetry.update();
    }
}
