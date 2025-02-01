package org.firstinspires.ftc.teamcode.extLib.autos.systems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import Wheelie.PID;
import Wheelie.Path;
import Wheelie.Pose2D;
import Wheelie.PathFollower;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class PathFollowerWrapper {

    private PathFollower follower;
    private final double lookAhead;

    private final PID xPID;
    private final PID yPID;
    private final PID hPID;
    private final ElapsedTime pidTimer;

    // P needs to be between 1/72 & 1/100
    private final double mP = 1.0/100., mI = 0.001, mD = 5,    //0.006
            hP = 1. / Math.toRadians(135), hI = 0.025, hD = 0.05,
            mMaxI = 0.15, hMaxI = 0.05;
    private Pose2D maintainPoint;

    //The max speed of the motors
    public double SPEED_PERCENT = 0.85;

    //The acceptable margin of error in inches and radians
    public final double MAX_TRANSLATION_ERROR = 2, MAX_ROTATION_ERROR = Math.toRadians (5);


    public PathFollowerWrapper (HardwareMap hw, Pose2D startPose, double look) {
        position = startPose;
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
        position = startPose;
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
        follower = new PathFollower (startPose, lookAhead, path, MAX_TRANSLATION_ERROR*.9, MAX_ROTATION_ERROR*.9);
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

        double movementAngle = getPose().h; //diff.h;

        double x = diff.x * Math.cos (movementAngle) - diff.y * Math.sin (movementAngle);
        double y = diff.x * Math.sin (movementAngle) + diff.y * Math.cos (movementAngle);
        double h = diff.h;

        if(Math.hypot(diff.x, diff.y) <= MAX_TRANSLATION_ERROR)
            x = y = 0;

        x*=mP;
        y*=mP;
        h*=hP;
        double total = Math.abs(x)+Math.abs(y)+Math.abs(h);
        if(total > SPEED_PERCENT || total < SPEED_PERCENT){
            double m = Math.max(Math.abs(x), Math.abs(y)),
                    max = Math.max(m, Math.abs(h));
            x*= SPEED_PERCENT/max;
            y*= SPEED_PERCENT/max;
            h*= SPEED_PERCENT/max;
        }

        return new double[] {
                x,y,h
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

        //Calculates the PID values based on error
        double x = xPID.pidCalc (move.x, getPose().x, time),
                y = yPID.pidCalc(move.y, getPose().y, time);

        double h = hPID.pidCalc (diff.h, 0, time);

        double movementAngle = getPose().h;//diff.h;
        double forward = x * Math.cos (movementAngle) - y * Math.sin (movementAngle),
                strafe = x * Math.sin (movementAngle) + y * Math.cos (movementAngle);

        if(Math.hypot(diff.x, diff.y) <= MAX_TRANSLATION_ERROR){
            forward = strafe = 0;
        }

        return new double[]{
                forward, strafe, h
        };
    }

    public void resetPidI () {
        xPID.resetI ();
        yPID.resetI ();
        hPID.resetI ();
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
        return position;
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

    private Pose2D position;
    public double[] follow(){

        if (follower != null) {
            //Checks if target is reached
            if (targetReached (follower.getLastPoint())) {
                maintainPoint = follower.getLastPoint();
                concludePath ();

                //Don't move, at target
                return new double[] {0,0,0};
            }

            //Calculates the movement vector

            if(!follower.approachingLast()
                    || Math.hypot(getPose().x-follower.getLastPoint().x,
                    getPose().y-follower.getLastPoint().y) > 10) {
                //Reshapes vector based on PID values
                m = follower.followPath (getPose());
                return moveTo(m.x, m.y, m.h);
            } else {
                return moveToPID (follower.getLastPoint(), pidTimer.time());
            }
        }

        //If there's no path, do not move
        return new double[] {0,0,0};
    }

    public double[] maintainPos(){
        return moveToPID(maintainPoint, pidTimer.time());
    }

    public void updatePose(Pose2D p){
        position = p;
    }

    @NonNull
    @Override
    public String toString () {
        return "MecDrivebase {" +
                ", mxPID=" + xPID +
                ", myPID=" + yPID +
                ", hPID=" + hPID +
                ", follower=" + follower +
                ", m=" + m +
                '}';
    }
}