package org.firstinspires.ftc.teamcode.extLib.wheelieExt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Wheelie.Pose2D;

public class Localization {
    //private Orientation angles; //TODO: FIND A REASON FOR THIS OR DELETE IT
    private final DcMotorEx hori, vert;

    private int prevH = 0, prevV = 0;
    private double prevHead = 0;

    //In millimeters
    public final static double WHEEL_DIAMETER = 32.0,
            WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    public final static int TICKS_PER_REV = 2000;
    public final static double MM_TO_INCH = 1.0/25.4;
    public final static double MM_PER_TICK = WHEEL_CIRCUMFERENCE / (double) TICKS_PER_REV;
    public final double X_MULT = 100.0/91.6;
    public final double Y_MULT = 100.0/97.3;

    // In inches
    public final static double H_DISTANCE_FROM_MID = 5.5; //TODO Check these values
    public final static double V_DISTANCE_FROM_MID = 7.5;

    public Pose2D currentPosition;
    private SparkFunOTOS sparkfunOTOS;


    public Localization (HardwareMap hw, Pose2D start) {
        //Sets the position the robot starts in
        currentPosition = new Pose2D (start.x, start.y, start.h);

        vert = hw.get (DcMotorEx.class, "br");
        hori = hw.get (DcMotorEx.class, "fr");
        hori.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vert.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hori.setMode (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vert.setMode (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        prevH = hori.getCurrentPosition();
        prevV = -vert.getCurrentPosition();

        //sparkfunOTOS = hw.get(SparkFunOTOS.class, "otos");
        //configureSparkFunOTOS();
    }
    private void configureSparkFunOTOS() {
        sparkfunOTOS.setLinearUnit(DistanceUnit.INCH);
        sparkfunOTOS.setAngularUnit(AngleUnit.DEGREES);
        sparkfunOTOS.setOffset(new SparkFunOTOS.Pose2D(0, 0, 0));
        sparkfunOTOS.setLinearScalar(1.0);
        sparkfunOTOS.setAngularScalar(1.0);
        sparkfunOTOS.resetTracking();
        sparkfunOTOS.setPosition(new SparkFunOTOS.Pose2D(0, 0, 0));
        sparkfunOTOS.calibrateImu(255, false);
    }
    public void updateWithSparkFunOTOS() {
        SparkFunOTOS.Pose2D sparkFunPos = sparkfunOTOS.getPosition();

        currentPosition.x = sparkFunPos.x;
        currentPosition.y = sparkFunPos.y;
        currentPosition.h = sparkFunPos.h;
    }

    /**
     * Calculates the current position based on odom pods and imu changes
     */
    private void calculateChanges (double angle) {
        //Finds the delta values in wheels and angle
        int currentH = hori.getCurrentPosition ();
        int currentV = -vert.getCurrentPosition ();
        int dy = currentH - prevH;
        int dx = currentV - prevV;
        double heading = AngleUnit.normalizeRadians (angle);
        double deltaHeading = AngleUnit.normalizeRadians(heading - prevHead);

        // Convert ticks to millimeters
        double dH = dy * MM_PER_TICK * MM_TO_INCH;
        double dV = dx * MM_PER_TICK * MM_TO_INCH;

        // Calculate the translation components
        double forward = dV + V_DISTANCE_FROM_MID * deltaHeading;
        double strafe = dH - H_DISTANCE_FROM_MID * deltaHeading;

        // Apply the rotation to the translation to convert to global coordinates
        double globalForward = forward * Math.cos (-heading) - strafe * Math.sin (-heading);
        double globalStrafe = forward * Math.sin (-heading) + strafe * Math.cos (-heading);

        // Update the current position
        currentPosition.x += globalForward * X_MULT;
        currentPosition.y += globalStrafe * Y_MULT        ;
        currentPosition.h = AngleUnit.normalizeRadians(deltaHeading + currentPosition.h);

        // Update previous encoder values
        prevH = currentH;
        prevV = currentV;
        prevHead = heading;
    }


    /**
     * @return the orientation of the robot in radians
     */
    /*public double getAngle () {
        angles = imu.getAngularOrientation (AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return AngleUnit.normalizeRadians (angles.firstAngle);
    }

     */

    public int getHori () {
        return hori.getCurrentPosition();
    }
    public int getVert () {
        return vert.getCurrentPosition();
    }

    public void update (double angle) {
        calculateChanges (angle);
    }

    public void resetPose(){
        currentPosition = new Pose2D(0,0,0);
    }
    public void resetPose(Pose2D a){
        currentPosition = a;
    }
}
//97
//97
//98