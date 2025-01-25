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

public class Board {
   private final DcMotor[] base = {null, null, null, null};

   private DcMotorEx lFulcrum, rFulcrum, extent;
   private final int fulMin = 15, fulMax = 800;
    public static final int subExt = 4000, netExt = -5400, defaultExt = 15;

   private Servo wristClaw, claw;

    private final IMU imu;

    public Board (HardwareMap hwMap) {
        rFulcrum = hwMap.get(DcMotorEx.class, "rightArm");
        lFulcrum = hwMap.get(DcMotorEx.class, "leftArm");
        rFulcrum.setDirection(DcMotorSimple.Direction.REVERSE);
        rFulcrum.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lFulcrum.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lFulcrum.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lFulcrum.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rFulcrum.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rFulcrum.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        extent = hwMap.get(DcMotorEx.class, "extension");
        extent.setDirection(DcMotorSimple.Direction.REVERSE);
        extent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extent.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        claw = hwMap.get(Servo.class, "claw");
        wristClaw = hwMap.get(Servo.class, "wrist");

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
                                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                        )
                )
        );
   }

    public void resetImu () {
        imu.resetYaw();
    }

    public double getDeg () {
        return imu.getRobotYawPitchRollAngles().getYaw();
    }

   public void sleep (long millis, OpMode op) { //TODO: IS THIS STILL NEEDED?
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
        final double theta = AngleUnit.normalizeRadians(Math.atan2(forward, right) - getDeg());

        drive(r * Math.sin(theta), r * Math.cos(theta), rotate);
    }
    public void drive(double forward, double right, double rotate, double dampen) {
        final double flp = (forward + right + rotate) * dampen;
        final double frp = (forward - right - rotate) * dampen;
        final double blp = (forward - right + rotate) * dampen;
        final double brp = (forward + right - rotate) * dampen;

        setPowers(flp, frp, blp, brp);
    }

    public void driveFieldRelative(double forward, double right, double rotate, double dampen) {
        final double r = Math.hypot(forward, right);
        final double theta = AngleUnit.normalizeRadians(Math.atan2(forward, right) - getDeg());

        drive(r * Math.sin(theta), r * Math.cos(theta), rotate, dampen);
    }

    public void powerArm(double power){
        if(braking && power != 0){
            braking = false;
            lFulcrum.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rFulcrum.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } else if(!braking && power == 0 ||
                getArmPosition() <= fulMin && power < 0 || getArmPosition() >= fulMax && power > 0){
            brakeArm();
        } else if (!braking){
            lFulcrum.setPower(power);
            rFulcrum.setPower(power);
        }
    }

    public boolean braking;
    public void brakeArm(){
        if(!braking){
            lFulcrum.setTargetPosition(lFulcrum.getCurrentPosition());
            rFulcrum.setTargetPosition(rFulcrum.getCurrentPosition());
            lFulcrum.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rFulcrum.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            braking = true;
        }
    }

    public void powerExtent(double power){
        if(extent.getMode() == DcMotor.RunMode.RUN_TO_POSITION)
            extent.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(power < 0 && extent.getCurrentPosition() >= -15 ||
            power > 0 && extent.getCurrentPosition() <= netExt)
            power = 0;
        extent.setPower(power);
    }

    public void setExtentTarget(int ticks){
        extent.setTargetPosition(ticks);
        extent.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extent.setPower(1);
    }

    public boolean runningToTarget(){
        return extent.getMode() == DcMotor.RunMode.RUN_TO_POSITION;
    }

    public int getTarget(){
        return extent.getTargetPosition();
    }

    public int getArmPosition(){
        return lFulcrum.getCurrentPosition();
    }

    public int getExtentPosition(){
        return extent.getCurrentPosition();
    }

    public double getExPower(){
        return extent.getPower();
    }

    public void setWristPosition(double pos){
        wristClaw.setPosition(pos);
    }
    public double getWristPosition(){
        return wristClaw.getPosition();
    }

    public void flipWrist(){
        setWristPosition(1 - wristClaw.getPosition());
    }

    public void setClawPosition(double pos){
        claw.setPosition(pos);
    }
    public double getClawPosition(){
        return claw.getPosition();
    }
}