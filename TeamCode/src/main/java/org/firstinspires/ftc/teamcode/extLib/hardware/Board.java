package org.firstinspires.ftc.teamcode.extLib.hardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Board {
    private final DcMotor[] base = {null, null, null, null};

    private final IMU imu;

    public Board (HardwareMap hwMap) {
            base[0] = hwMap.get(DcMotor.class, "fl");
            base[1] = hwMap.get(DcMotor.class, "fr");
            base[2] = hwMap.get(DcMotor.class, "br");
            base[3] = hwMap.get(DcMotor.class, "bl");

            base[0].setDirection(DcMotorSimple.Direction.REVERSE);
            base[1].setDirection(DcMotorSimple.Direction.FORWARD);
            base[2].setDirection(DcMotorSimple.Direction.FORWARD);
            base[3].setDirection(DcMotorSimple.Direction.REVERSE);

            base[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            base[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            base[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            base[1].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            base[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            base[2].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            base[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            base[3].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            imu = hwMap.get(IMU.class, "imu");

            imu.initialize(
                    new IMU.Parameters(
                            new RevHubOrientationOnRobot(
                                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                            )
                    )
            );
    }

    public double getAngle() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public void resetImu () {
        imu.resetYaw();
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
        double flp = forward + right + rotate;
        double frp = forward - right - rotate;
        double blp = forward - right + rotate;
        double brp = forward + right - rotate;

        setPowers(flp, frp, blp, brp);
    }

    public void driveFieldRelative(double forward, double right, double rotate) {
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(forward, right);

        theta = AngleUnit.normalizeRadians(theta - getAngle());

        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);

        drive(newForward, newRight, rotate);
    }

    public int getDrivePosition(int index){
        return base[index].getCurrentPosition();
    }
}