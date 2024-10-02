package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Objects;

public class Board {
    private final DcMotor[] drivebase = {null, null, null, null};
    private DcMotorEx spool = null;

    private Servo claw = null;

    private IMU imu = null;

    public void init(HardwareMap hwMap) {
        HashMap<String, Throwable> fails = new HashMap<>();

        try {
            drivebase[0] = hwMap.get(DcMotor.class, "fl");
            drivebase[1] = hwMap.get(DcMotor.class, "fr");
            drivebase[2] = hwMap.get(DcMotor.class, "br");
            drivebase[3] = hwMap.get(DcMotor.class, "bl");

            drivebase[0].setDirection(DcMotorSimple.Direction.REVERSE);
            drivebase[1].setDirection(DcMotorSimple.Direction.FORWARD);
            drivebase[2].setDirection(DcMotorSimple.Direction.FORWARD);
            drivebase[3].setDirection(DcMotorSimple.Direction.REVERSE);
        } catch (Throwable e) {
            fails.put("The Drivebase", e);
        }

        try {
            spool = hwMap.get(DcMotorEx.class, "spool");

            spool.setDirection(DcMotorSimple.Direction.FORWARD);
            spool.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } catch (Throwable e) {
            fails.put("Spool Motor", e);
        }

        try {
            claw = hwMap.get(Servo.class, "claw");
        } catch (Throwable e) {
            fails.put("Gripper", e);
        }

        try {
            imu = hwMap.get(IMU.class, "imu");

            imu.initialize(
                    new IMU.Parameters(
                            new RevHubOrientationOnRobot(
                                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                            )
                    )
            );
        } catch (Throwable e) {
            fails.put("IMU", e);
        }

        try {
            FileWriter writer = new FileWriter("BOARD_LOG.txt", false);

            for (String dev : fails.keySet()) {
                writer.write(dev + " : " + Objects.requireNonNull(fails.get(dev)) + "\n");
            }

            writer.close();
        } catch (Throwable ignored) {
        }
    }

    public void setSpoolPower (double power) {
        spool.setPower(power);
    }

    public int getSpoolPostion () {
        return spool.getCurrentPosition();
    }

    public void setClaw (boolean open) {
        if (open) {
            claw.setPosition(0); //open position, according to Caleb
        } else {
            claw.setPosition(1); //thus follows that this is the closed position
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

        drivebase[0].setPower(flp);
        drivebase[1].setPower(frp);
        drivebase[2].setPower(brp);
        drivebase[3].setPower(blp);
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
}
