package org.firstinspires.ftc.teamcode.extLib.wheelieExt;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import Wheelie.Pose2D;

public class Localization {
    private Orientation angles;

    private final DcMotorEx hori, vert;

    private int prevH = 0, prevV = 0;
    private double prevHead = 0;

    //In millimeters
    public final static double WHEEL_DIAMETER = 32.0,
            WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    public final static int TICKS_PER_REV = 2000;
    public final static double MM_TO_INCH = 1.0/25.4;
    public final static double MM_PER_TICK = WHEEL_CIRCUMFERENCE / (double) TICKS_PER_REV;

    // In inches
    public final static double H_DISTANCE_FROM_MID = 5.75; //TODO Check these values
    public final static double V_DISTANCE_FROM_MID = 2;

    public Pose2D currentPosition;

    public Localization (HardwareMap hw, Pose2D start) {
        //Sets the position the robot starts in
        currentPosition = new Pose2D (start.x, start.y, start.h);

        hori = hw.get (DcMotorEx.class, "fr");
        vert = hw.get (DcMotorEx.class, "br");
        hori.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vert.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hori.setMode (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vert.setMode (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Calculates the current position based on odom pods and imu changes
     */
    private void calculateChanges (double angle) {
        //Finds the delta values in wheels and angle
        int currentH = -vert.getCurrentPosition ();
        int currentV = -hori.getCurrentPosition ();
        int dy = currentH - prevH;
        int dx = currentV - prevV;
        double heading = AngleUnit.normalizeRadians (angle);
        double deltaHeading = heading - prevHead;

        // Convert ticks to millimeters
        double dH = dy * MM_PER_TICK;
        double dV = dx * MM_PER_TICK;

        // Calculate the translation components
        double forward = dV - V_DISTANCE_FROM_MID * deltaHeading;
        double strafe = dH + H_DISTANCE_FROM_MID * deltaHeading;

        // Apply the rotation to the translation to convert to global coordinates
        double globalForward = forward * Math.cos (heading) - strafe * Math.sin (heading);
        double globalStrafe = forward * Math.sin (heading) + strafe * Math.cos (heading);

        // Update the current position
        currentPosition.x += globalForward * MM_TO_INCH;
        currentPosition.y += globalStrafe * MM_TO_INCH;
        currentPosition.h = deltaHeading + currentPosition.h;

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
        return 0;
    }
    public int getVert () {
        return 0;
    }

    public void update (double angle) {
        calculateChanges (angle);
    }
}
