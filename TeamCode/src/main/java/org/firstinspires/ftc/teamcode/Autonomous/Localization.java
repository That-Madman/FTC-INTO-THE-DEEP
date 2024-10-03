package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import Wheelie.Pose2D;

public class Localization {
    private DcMotorEx hori, vert;
    private BNO055IMU imu;
    private Orientation angles;

    private int prevH = 0, prevV = 0;

    //In millimeters
    public final static double WHEEL_DIAMETER = 48.0,
            WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    public final static int TICKS_PER_REV = 2000;
    public final static double MM_TO_INCH = 1.0/25.4;
    public final static double MM_PER_TICK = WHEEL_CIRCUMFERENCE / (double) TICKS_PER_REV;

    // In inches
    public final static double H_DISTANCE_FROM_MID = 10.25; //TODO Check these values
    public final static double V_DISTANCE_FROM_MID = 6.55;

    public Pose2D currentPosition;

    public Localization(HardwareMap hw, Pose2D start){
        //Sets the position the robot starts in
        currentPosition = new Pose2D(start.x, start.y, start.h);

        //Setting up Odom pods
        hori = hw.get(DcMotorEx.class, "BR"); //TODO Find where odom is wired
        vert = hw.get(DcMotorEx.class, "FR");
        hori.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vert.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Initializing IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";

        imu = hw.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    /**
     * Calculates the current position based on odom pods and imu changes
     */
    private void calculateChanges() {
        //Finds the delta values in wheels and angle
        int currentH = hori.getCurrentPosition();
        int currentV = -vert.getCurrentPosition();
        int dy = currentH - prevH;
        int dx = currentV - prevV;
        double heading = getAngle();
        double deltaHeading = heading - currentPosition.h;

        // Convert ticks to millimeters
        double dH = dy * MM_PER_TICK;
        double dV = dx * MM_PER_TICK;

        // Calculate the translation components
        double forward = dV - V_DISTANCE_FROM_MID * deltaHeading;
        double strafe = dH + H_DISTANCE_FROM_MID * deltaHeading;

        // Apply the rotation to the translation to convert to global coordinates
        double globalForward = forward * Math.cos(heading) - strafe * Math.sin(heading);
        double globalStrafe = forward * Math.sin(heading) + strafe * Math.cos(heading);

        // Update the current position
        currentPosition.x += globalForward * MM_TO_INCH;
        currentPosition.y += globalStrafe * MM_TO_INCH;
        currentPosition.h = heading;

        // Update previous encoder values
        prevH = currentH;
        prevV = currentV;
    }


    /**
     * @return the orientation of the robot in radians
     */
    public double getAngle(){
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return -(angles.firstAngle + 2 * Math.PI) % (2 * Math.PI);
    }

    public int getHori(){
        return hori.getCurrentPosition();
    }
    public int getVert(){
        return vert.getCurrentPosition();
    }

    public void update(){
        calculateChanges();
    }
}