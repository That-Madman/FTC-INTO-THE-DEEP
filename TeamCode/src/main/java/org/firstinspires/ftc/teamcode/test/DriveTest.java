package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp (group = "Tests")
public class DriveTest extends OpMode {
    private boolean driveFieldRel = true;

    private boolean bHeld;

    private final DcMotor[] base = {null, null, null, null};

    private IMU imu;


    @Override
    public void init () {
        base[0] = hardwareMap.get(DcMotor.class, "fl");
        base[1] = hardwareMap.get(DcMotor.class, "fr");
        base[2] = hardwareMap.get(DcMotor.class, "br");
        base[3] = hardwareMap.get(DcMotor.class, "bl");

        base[0].setDirection(DcMotorSimple.Direction.REVERSE);
        base[1].setDirection(DcMotorSimple.Direction.FORWARD);
        base[2].setDirection(DcMotorSimple.Direction.REVERSE);
        base[3].setDirection(DcMotorSimple.Direction.REVERSE);

        base[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[1].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[2].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[3].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        imu = hardwareMap.get(IMU.class, "imu");
    }

    @Override
    public void loop () {
        if (gamepad1.left_bumper) {
            if (driveFieldRel) {
                driveFieldRelative(
                        -gamepad1.left_stick_y * 0.5,
                        gamepad1.left_stick_x * 0.5,
                        gamepad1.right_stick_x * 0.5
                );

                telemetry.addData("Driving", "Field Relative");
            } else {
                drive(
                        -gamepad1.left_stick_y * 0.5,
                        gamepad1.left_stick_x * 0.5,
                        gamepad1.right_stick_x * 0.5
                );
                telemetry.addData("Driving", "Robot Relative");
            }
        } else {
            if (driveFieldRel) {
                driveFieldRelative(
                        -gamepad1.left_stick_y,
                        gamepad1.left_stick_x,
                        gamepad1.right_stick_x
                );
                telemetry.addData("Driving", "Field Relative");
            } else {
                drive(
                        -gamepad1.left_stick_y,
                        gamepad1.left_stick_x,
                        gamepad1.right_stick_x
                );
                telemetry.addData("Driving", "Robot Relative");
            }

            driveFieldRel = gamepad1.b && !bHeld;
            bHeld = gamepad1.b;
        }
    }

    public double getAngle() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }


    public void setPowers(double flp, double frp, double blp, double brp) {
        double maxSpeed = 1;

        maxSpeed = Math.max(maxSpeed, Math.abs(flp));
        maxSpeed = Math.max(maxSpeed, Math.abs(frp));
        maxSpeed = Math.max(maxSpeed, Math.abs(brp));
        maxSpeed = Math.max(maxSpeed, Math.abs(blp));

        flp /= maxSpeed;
        frp /= maxSpeed;
        brp /= maxSpeed;
        blp /= maxSpeed;

        base[0].setPower(flp);
        base[1].setPower(frp);
        base[2].setPower(brp);
        base[3].setPower(blp);
    }

    public void drive(double forward, double right, double rotate) {
        final double flp = forward + right + rotate;
        final double frp = forward - right - rotate;
        final double blp = forward - right + rotate;
        final double brp = forward + right - rotate;

        setPowers(flp, frp, blp, brp);
    }

    public void driveFieldRelative(double forward, double right, double rotate) {
        double theta = Math.atan2(forward, right);
        final double r = Math.hypot(forward, right);

        theta = AngleUnit.normalizeRadians(theta - getAngle());

        final double newForward = r * Math.sin(theta);
        final double newRight = r * Math.cos(theta);

        drive(newForward, newRight, rotate);
    }

}
