package org.firstinspires.ftc.teamcode.extLib;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import Wheelie.Pose2D;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Board {
    private final DcMotorEx[] drivebase = {null, null, null, null};

    private final IMU imu;
    private final SparkFunOTOS sparkFunOTOS;

    private final double ticksPerRev = 537.6;

    public Board(HardwareMap hwMap) {
        drivebase[0] = hwMap.get(DcMotorEx.class, "fl");
        drivebase[1] = hwMap.get(DcMotorEx.class, "fr");
        drivebase[2] = hwMap.get(DcMotorEx.class, "br");
        drivebase[3] = hwMap.get(DcMotorEx.class, "bl");

        drivebase[0].setDirection(DcMotorSimple.Direction.REVERSE);
        drivebase[1].setDirection(DcMotorSimple.Direction.FORWARD);
        drivebase[2].setDirection(DcMotorSimple.Direction.FORWARD);
        drivebase[3].setDirection(DcMotorSimple.Direction.FORWARD);

        drivebase[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivebase[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drivebase[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivebase[1].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drivebase[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivebase[2].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drivebase[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivebase[3].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sparkFunOTOS = hwMap.get(SparkFunOTOS.class, "otos");
        configureSensor();

        imu = hwMap.get(IMU.class, "imu");

        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                                RevHubOrientationOnRobot.UsbFacingDirection.UP
                        )
                )
        );
    }

    public Board(HardwareMap hwMap, DcMotor.RunMode runMode) {
        drivebase[0] = hwMap.get(DcMotorEx.class, "fl");
        drivebase[1] = hwMap.get(DcMotorEx.class, "fr");
        drivebase[2] = hwMap.get(DcMotorEx.class, "br");
        drivebase[3] = hwMap.get(DcMotorEx.class, "bl");

        drivebase[0].setDirection(DcMotorSimple.Direction.REVERSE);
        drivebase[1].setDirection(DcMotorSimple.Direction.FORWARD);
        drivebase[2].setDirection(DcMotorSimple.Direction.FORWARD);
        drivebase[3].setDirection(DcMotorSimple.Direction.FORWARD);

        drivebase[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivebase[0].setMode(runMode);
        drivebase[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivebase[1].setMode(runMode);
        drivebase[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivebase[2].setMode(runMode);
        drivebase[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivebase[3].setMode(runMode);
        sparkFunOTOS = hwMap.get(SparkFunOTOS.class, "otos");
        configureSensor();

        imu = hwMap.get(IMU.class, "imu");

        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                                RevHubOrientationOnRobot.UsbFacingDirection.UP
                        )
                )
        );
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
    public void setVelocities(double fl, double fr, double bl, double br){
        drivebase[0].setVelocity(fl*ticksPerRev);
        drivebase[1].setVelocity(fr*ticksPerRev);
        drivebase[2].setVelocity(bl*ticksPerRev);
        drivebase[3].setVelocity(br*ticksPerRev);
    }

    public void drive(double forward, double right, double rotate) {
        final double flp = forward + right + rotate;
        final double frp = forward - right - rotate;
        final double blp = forward - right + rotate;
        final double brp = forward + right - rotate;

        setPowers(flp, frp, blp, brp);
    }
    public void driveVel(double forward, double rotate, double right) {
        final double flp = forward + right + rotate;
        final double frp = forward - right - rotate;
        final double blp = forward - right + rotate;
        final double brp = forward + right - rotate;

        setVelocities(flp, frp, blp, brp);
    }
    private void configureSensor() {
        sparkFunOTOS.setLinearUnit(DistanceUnit.INCH);
        sparkFunOTOS.setAngularUnit(AngleUnit.RADIANS);
        sparkFunOTOS.setOffset(new Pose2D(3.5, 6, 0));
        sparkFunOTOS.setLinearScalar(100.0/93.6);
        sparkFunOTOS.setAngularScalar((Math.PI*10)/(Math.PI*10+.494));
        sparkFunOTOS.resetTracking();
        sparkFunOTOS.setPosition(new Pose2D(0,0,0));
        sparkFunOTOS.calibrateImu(255, false);
    }

    public Pose2D getCurrentPose() {
        return sparkFunOTOS.getPosition();
    }

    public void resetIMU() {
        imu.resetYaw();
        //sparkfunOTOS.calibrateImu(255, false);
    }

    public void driveFieldRelative(double forward, double right, double rotate) {
        double theta = Math.atan2(forward, right);
        final double r = Math.hypot(forward, right);

        theta = AngleUnit.normalizeRadians(theta - getAngle());

        final double newForward = r * Math.sin(theta);
        final double newRight = r * Math.cos(theta);

        drive(newForward, newRight, rotate);
    }

    public int getDrivePosition(int index) {
        return drivebase[index].getCurrentPosition();
    }

    public double getVelocity(int index) {
        return drivebase[index].getVelocity();
    }
}