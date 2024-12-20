package org.firstinspires.ftc.teamcode.extLib.wheelieExt;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import Wheelie.PID;
import Wheelie.Path;
import Wheelie.Pose2D;
//import Wheelie.PathFollower;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import  org.firstinspires.ftc.teamcode.extLib.PathFollower;

public class PathFollowerWrapper {
    private final Localization localization;

    private PathFollower follower;
    private double lookAhead;

    //TODO Set start time and cap I
    private final PID xPID;
    private final PID yPID;
    private final PID hPID;
    private final ElapsedTime pidTimer;

    private final double mP = 1.0/25.0, mI = 0, mD = 0.004,
            hP = 1. / Math.toRadians(45), hI = 0.0001, hD = .005,
            mMaxI = 0.001, hMaxI = 0.05;
    private boolean xi, yi, hi;

    //The max speed of the motors
    public double SPEED_PERCENT = 1;

    //The acceptable margin of error in inches and radians
    public final double MAX_TRANSLATION_ERROR = 2, MAX_ROTATION_ERROR = Math.toRadians (5);


    public PathFollowerWrapper (HardwareMap hw, Pose2D startPose, double look) {
        localization = new Localization (hw, startPose);
        lookAhead = look;

        xPID = new PID (mP,mI,mD);
        yPID = new PID (mP,mI,mD);
        hPID = new PID (hP,hI,hD);
        xPID.capI (mMaxI);
        yPID.capI (mMaxI);
        hPID.capI (hMaxI);
        pidTimer = new ElapsedTime (ElapsedTime.Resolution.MILLISECONDS);
    }

    public PathFollowerWrapper (HardwareMap hw, Pose2D startPose, double look, double maxSpeed) {
        localization = new Localization (hw, startPose);
        lookAhead=look;

        SPEED_PERCENT = maxSpeed;

        xPID = new PID (mP,mI,mD);
        yPID = new PID (mP,mI,mD);
        hPID = new PID (hP,hI,hD);
        xPID.capI (mMaxI);
        yPID.capI (mMaxI);
        hPID.capI (hMaxI);
        pidTimer = new ElapsedTime (ElapsedTime.Resolution.MILLISECONDS);
    }

    /** Initializes a new path to follow */
    public void setPath (Pose2D startPose, Path path) {
        follower = new PathFollower (startPose, lookAhead, path, MAX_TRANSLATION_ERROR-1, MAX_ROTATION_ERROR-Math.toRadians(1));
        resetPidI();
        pidTimer.reset ();
    }

    /** Sets motor powers so drivebase can move towards target based on input (usually from the PathFollower class)*/
    public double[] moveTo (double forward, double strafe, double heading) {
        //Rotates the vector based on robot's heading
        Pose2D diff = new Pose2D(
                forward-getPose().x,
                strafe-getPose().y,
                AngleUnit.normalizeRadians(heading - getPose().h)
        );

        double x = diff.x * Math.cos (-getPose ().h) - diff.y * Math.sin (-getPose ().h);
        double y = diff.x * Math.sin (-getPose ().h) + diff.y * Math.cos (-getPose ().h);
        double h = diff.h;

        /*if (Math.hypot (x,y) < MAX_TRANSLATION_ERROR) { //Stops translational movement, focus on heading
            x = 0;
            y = 0;
        } else { //Minimizes the heading control
            h *= hP;
        }*/

        return new double[] {
                x, -y, h
        };
    }

    /** Sets motor powers so drivebase can move towards target using PID (for when the lookahead is shrinking)
     * @param move output from PathFollower class, followPath method
     */
    public double[] moveToPID (Pose2D move, double time) {
        Pose2D diff = new Pose2D(
                move.x-getPose().x,
                move.y-getPose().y,
                AngleUnit.normalizeRadians(move.h-getPose().h)
        );

        //double x = 0;// move.x * Math.cos (getPose ().h) - move.y * Math.sin (getPose ().h);
        //double y = 0;//move.x * Math.sin (getPose ().h) + move.y * Math.cos (getPose ().h);

        //Calculates the PID values based on error
        double x = xPID.pidCalc (move.x, getPose().x, time),
            y = yPID.pidCalc(move.y, getPose().y, time);

        double h = hPID.pidCalc (diff.h, 0, time); //changed diff.h from move.h and changed 0 from getPose().h

        double forward = x * Math.cos (getPose ().h) - y * Math.sin (getPose ().h),
                strafe = x * Math.sin (getPose ().h) + y * Math.cos (getPose ().h);

        return new double[]{
                forward, -strafe, h
        };
    }

    public void resetPidI () {
        xPID.resetI ();
        yPID.resetI ();
        hPID.resetI ();
        xi = false;
        yi = false;
        hi = false;
    }

    /** Checks if the target pose is within error margin */
    public boolean targetReached (Pose2D target) {
        return Math.hypot (target.x-getPose ().x, target.y-getPose ().y) <= MAX_TRANSLATION_ERROR &&
                Math.abs (AngleUnit.normalizeRadians(target.h-getPose ().h)) <= MAX_ROTATION_ERROR;
    }

    public boolean rotateReached (Pose2D target) {
        return Math.abs (target.h-getPose ().h) <= MAX_ROTATION_ERROR;
    }
    public void concludePath () {
        follower = null;
    }

    public PathFollower getFollower () {
        return follower;
    }

    public Pose2D getPose () {
        return position;//return localization.currentPosition;
    }
    public String getPoseString () {
        return localization.currentPosition.x + ", " +
                localization.currentPosition.y + ", " +
                localization.currentPosition.h;
    }

    public Localization getLocalization(){
        return localization;
    }

    public int getHoriOdom () {
        return localization.getHori ();
    }
    public int getVertOdom () {
        return localization.getVert ();
    }

    private Pose2D m;

    public Pose2D followerValues () {
        return m;
    }

    public int getCurrentWayPoint () {
        return follower.getWayPoint ();
    }

    /** Calculates and outputs the movement vector */
    public double[] followPath () {
        if (follower != null) {
            //Checks if target is reached
            if (targetReached (follower.getLastPoint ())) {
                concludePath ();

                //Don't move, at target
                return new double[] {0,0,0};
            }
            //Calculates the movement vector
            m = follower.followPath (getPose());
            //Reshapes vector based on error and rotation
            return moveTo (m.x, m.y, m.h);
        }

        //If there's no path, do not move
        return new double[] {0,0,0};
    }

    /** Calculates and outputs the movement vector using PID */
    public double[] followPathPID () {

        if (follower != null) {
            //Checks if target is reached
            if (targetReached (follower.getLastPoint ())) {
                concludePath ();

                //Don't move, at target
                return new double[] {0,0,0};
            }

            if(!follower.approachingLast()) {
                //Calculates the movement vector
                m = follower.followPath (getPose());
                //Reshapes vector based on PID values
                return moveToPID (m, pidTimer.time());
            } else {
                return moveToPID(follower.getLastPoint(), pidTimer.time());
            }
        }

        //If there's no path, do not move
        return new double[] {0,0,0};
    }

    public Pose2D position;
    public double[] follow(){

        if (follower != null) {
            //Checks if target is reached
            if (targetReached (follower.getLastPoint())) {
                concludePath ();

                //Don't move, at target
                return new double[] {0,0,0};
            }

            //Calculates the movement vector
            m = follower.followPath (getPose());
            if(!follower.approachingLast()) {
                //Reshapes vector based on PID values
                return moveTo(m.x, m.y, m.h);
            } else {
                return moveToPID (m, pidTimer.time());
            }
        }

        //If there's no path, do not move
        return new double[] {0,0,0};
    }

    /** Updates the localization of the robot */
    public void updatePose (double angle) {
        localization.update (angle);
    }

    public void updatePose(Pose2D p){
        position = p;
    }

    public void resetPose(){
        localization.resetPose();
    }
    public void resetPose(Pose2D a){
        localization.resetPose(a);
    }

    @NonNull
    @Override
    public String toString () {
        return "MecDrivebase {" +
                ", localization=" + localization +
                ", mxPID=" + xPID +
                ", myPID=" + yPID +
                ", hPID=" + hPID +
                ", follower=" + follower +
                ", m=" + m +
                '}';
    }
}
