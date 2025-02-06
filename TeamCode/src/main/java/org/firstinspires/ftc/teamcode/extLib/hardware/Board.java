package org.firstinspires.ftc.teamcode.extLib.hardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Wheelie.Pose2D;

public class Board {
    Servo bigGrab;
    Servo lRot;
    Servo mRot;
    Servo pick;
    Servo r1, r2;
    CRServo s1, s2;
    Servo rRot;
    Servo swivel;
    Servo tinyGrab;
    ColorRangeSensor cSensor;
    ColorRangeSensor cSensor2;

    private final DcMotorEx v1, v2;
    private final DcMotor[] base = {null, null, null, null};

    private final IMU imu;

    private final TouchSensor v1t, v2t;

    private final TouchSensor t1, t2;

    private final SparkFunOTOS sparkFunOTOS;

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

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

            s1 = hwMap.get(CRServo.class, "s1");
            s2 = hwMap.get(CRServo.class, "s2");


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
        setSwivel((byte) 2);

        v1t = hwMap.get(TouchSensor.class, "v1t");
        v2t = hwMap.get(TouchSensor.class, "v2t");

        t1 = hwMap.get(TouchSensor.class, "t1");
        t2 = hwMap.get(TouchSensor.class, "t2");

        /* TODO: UNCOMMENT WHEN NEEDED
        cSensor = hwMap.get(ColorRangeSensor.class, "cSensor");
        cSensor2 = hwMap.get(ColorRangeSensor.class, "Csensor2");
         */

//        WebcamName webcamName = hwMap.get(WebcamName.class, "Webcam 1");
//        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
//        visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTagProcessor);
    }

    public void driveEncInit () {
        for (DcMotor m : base) {
            //TODO: UNCOMMENT IF DRIVEBASE ENCODERS EVER ADDED
         //   m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    private void configureSensor() {
        sparkFunOTOS.setLinearUnit(DistanceUnit.INCH);
        sparkFunOTOS.setAngularUnit(AngleUnit.RADIANS);
        sparkFunOTOS.setOffset(new Pose2D(0, 0, 0));
        sparkFunOTOS.setLinearScalar(1.0);
        sparkFunOTOS.setAngularScalar((Math.PI * 20) / (Math.PI * 20 + 0.494));
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

    public void setAscentScrews (double pow1, double pow2) {
        s1.setPower(pow1);
        s2.setPower(pow2);
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
        bigGrab.setPosition(c ? 1 : 0);
    }

    public void setRot (byte u) {
        switch (u) {
            case 0:
                lRot.setPosition(0);
                rRot.setPosition(0);
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
                lRot.setPosition(0.15);
                rRot.setPosition(0.15);
                mRot.setPosition(1);
                break;
        }
    }

    public void setPick (boolean c) {
        pick.setPosition(c ? 0.5 : 1);
    }

    public boolean getTouched () {
        return t1.isPressed() && t2.isPressed();
    }

    public void setSwivel (byte s) {
        switch (s) {
            case 0:
                swivel.setPosition(0.2);
                break;
            case 1:
                swivel.setPosition(0.7);
                break;
            case 2:
                swivel.setPosition(1);
                break;
        }
    }

    /** Use the numbers 0-2 to select which color you want the color sensor to search for
     * @param target 0: Yellow, Blue 1:, Red: 2
     */
    public boolean SearchForColor(byte target) {
        switch(target) {
            case 0:
                return ((cSensor.red() + cSensor.green()) / 2 > cSensor.blue() * 2) && ((cSensor2.red() + cSensor2.green()) / 2 > cSensor2.blue() * 2);
            case 1:
                return (cSensor.blue() > cSensor.red() + cSensor.green()) && (cSensor2.blue() > cSensor2.red() + cSensor2.green());
            case 2:
                return (cSensor.red() * 1.5 > cSensor.blue() + cSensor.green()) && (cSensor2.red() * 1.5 > cSensor2.blue() + cSensor2.green());
            default:
                return false;
        }
    }

    public void genericCameraThingy(Telemetry telemetry) {
        List <AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
        StringBuilder idsFound = new StringBuilder();
          for (AprilTagDetection detection : currentDetections) {
             idsFound.append(detection.id);
             idsFound.append(' ');
              if (detection.metadata != null) {
                  telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                  telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                  telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                  telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
              } else {
                  telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                  telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
              }

    }}

    public void sleep (long millis, OpMode op) {
        op.resetRuntime();
        while (op.getRuntime() < (double) millis / 1000);
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













