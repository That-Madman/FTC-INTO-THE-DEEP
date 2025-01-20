package org.firstinspires.ftc.teamcode.extLib.hardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Wheelie.Pose2D;

public class Board {
    Servo bigGrab;
    Servo lRot;
    Servo mRot;
    Servo pick;
    Servo r1, r2;
    Servo rRot;
    Servo swivel;
    Servo tinyGrab;

    private final DcMotorEx v1, v2;
    private final DcMotor[] base = {null, null, null, null};

    private final IMU imu;

    private final TouchSensor v1t, v2t;

    private final TouchSensor t1, t2;

    private final SparkFunOTOS sparkFunOTOS;

    public Board (HardwareMap hwMap) {
            base[0] = hwMap.get(DcMotor.class, "fl");
            base[1] = hwMap.get(DcMotor.class, "fr");
            base[2] = hwMap.get(DcMotor.class, "br");
            base[3] = hwMap.get(DcMotor.class, "bl");

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

            v1 = hwMap.get(DcMotorEx.class, "v1");
            v2 = hwMap.get(DcMotorEx.class, "v2");

            v1.setDirection(DcMotorSimple.Direction.REVERSE);
            v2.setDirection(DcMotorSimple.Direction.FORWARD);

            v1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            v2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            v1.setPower(0.7);
            v2.setPower(0.7);
            v1.setTargetPosition(0);
            v2.setTargetPosition(0);
            v1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            v2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            imu = hwMap.get(IMU.class, "imu");

            imu.initialize(
                    new IMU.Parameters(
                            new RevHubOrientationOnRobot(
                                    RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                                    RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                            )
                    )
            );

        sparkFunOTOS = hwMap.get(SparkFunOTOS.class, "otos");
        configureSensor();

        tinyGrab = hwMap.get(Servo.class, "tinyGrab");
        bigGrab = hwMap.get(Servo.class, "bigGrab");
        lRot = hwMap.get(Servo.class, "lRot");
        rRot = hwMap.get(Servo.class, "rRot");
        mRot = hwMap.get(Servo.class, "mRot");

        lRot.setDirection(Servo.Direction.REVERSE);
        setRot((byte) 0);
        setTinyGrab(false);
        setBigGrab(false);

        r1 = hwMap.get(Servo.class, "reachy");
        r2 = hwMap.get(Servo.class, "reachier");

        r2.setDirection(Servo.Direction.REVERSE);

        pick = hwMap.get(Servo.class, "pick");
        swivel = hwMap.get(Servo.class, "swivel");

        setPick(false);
        setSwivel((byte) 1);

        v1t = hwMap.get(TouchSensor.class, "v1t");
        v2t = hwMap.get(TouchSensor.class, "v2t");

        t1 = hwMap.get(TouchSensor.class, "t1");
        t2 = hwMap.get(TouchSensor.class, "t2");
    }

    private void configureSensor() {
        sparkFunOTOS.setLinearUnit(DistanceUnit.INCH);
        sparkFunOTOS.setAngularUnit(AngleUnit.RADIANS);
        sparkFunOTOS.setOffset(new Pose2D(0, 0, 0));
        sparkFunOTOS.setLinearScalar(1.0);// 95.9381, 98.1368, 98.281, 99.903
        //sparkFunOTOS.setLinearScalar(100./95.016);
        sparkFunOTOS.setAngularScalar((Math.PI*20)/(Math.PI*20+.494));
        sparkFunOTOS.resetTracking();
        sparkFunOTOS.setPosition(new Pose2D(0,0,0));
        sparkFunOTOS.calibrateImu(255, false);
    }

    public Pose2D getCurrentPose() {
        return sparkFunOTOS.getPosition();
    }

    public double getAngle() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public int getLiftPos (boolean rl) {
        return (rl ? v1 : v2).getCurrentPosition();
    }

    public void resetLift (boolean rl) {
        if (rl) {
            v1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            v1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            v2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            v2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public boolean getLiftTouched (boolean rl) {
        return (rl ? v1t : v2t).isPressed();
    }

    public void setLiftDampen (float damp) {
            v1.setPower(damp);
            v2.setPower(damp);
    }

    public void resetImu () {
        imu.resetYaw();
    }

    public void setLift (int height) {
        v1.setTargetPosition(height);
        v2.setTargetPosition(height);
    }

    public void setReach (byte r) {
        switch (r) {
            case 0:
                r1.setPosition(1);
                r2.setPosition(1);
                break;

            case 1:
                r1.setPosition(0.5);
                r2.setPosition(0.5);
                break;

            case 2:
                r1.setPosition(0.1);
                r2.setPosition(0.1);
                break;
        }
    }

    public void setTinyGrab (boolean c) {
        tinyGrab.setPosition(c ? 0.25 : 1);
    }

    public void setBigGrab (boolean c) {
        bigGrab.setPosition(c ? 1 : 0.5);
    }

    public void setRot (byte u) {
        switch (u) {
            case 0:
                lRot.setPosition(0.1);
                rRot.setPosition(0.1);
                mRot.setPosition(0);
                break;
            case 1:
                lRot.setPosition(1);
                rRot.setPosition(1);
                mRot.setPosition(0);
                break;
            case 2:
                lRot.setPosition(0.75);
                rRot.setPosition(0.75);
                mRot.setPosition(1);
                break;
            case 4:
                lRot.setPosition(0.7);
                rRot.setPosition(0.7);
                mRot.setPosition(0);
                break;
            case 3:
                lRot.setPosition(0.2);
                rRot.setPosition(0.2);
                mRot.setPosition(1);
                break;
        }
    }

    public void setPick (boolean c) {
        pick.setPosition(c ? 0.5 : 1);
    }

    public void setSwivel (byte s) {
        switch (s) {
            case 0:
                swivel.setPosition(0.2);
                break;
            case 1:
                swivel.setPosition(0.5);
                break;
            case 2:
                swivel.setPosition(1);
                break;
        }
    }

    public void sleep (long millis, OpMode op) {
        op.resetRuntime();
        while (op.getRuntime() < (double) millis / 1000);
    }

    public boolean getTouched () {
        return t1.isPressed() && t2.isPressed();
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
        final double r = Math.hypot(forward, right);
        final double theta = AngleUnit.normalizeRadians(Math.atan2(forward, right) - getAngle());

        drive(r * Math.sin(theta), r * Math.cos(theta), rotate);
    }
}