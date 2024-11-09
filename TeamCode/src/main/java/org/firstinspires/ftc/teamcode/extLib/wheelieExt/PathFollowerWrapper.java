package org.firstinspires.ftc.teamcode.extLib.wheelieExt;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import Wheelie.PID;
import Wheelie.Path;
import Wheelie.Pose2D;
import Wheelie.PathFollower;

public class PathFollowerWrapper {
    private final Localization localization;

    private PathFollower follower;
    private final double lookAhead;

    //TODO Set start time and cap I
    private final PID xPID;
    private final PID yPID;
    private final PID hPID;
    private final ElapsedTime pidTimer;

    private final double mP = (double) 1 / 24, mI = 0, mD = 0,
            hP = (double) 1 / Math.PI, hI = 0, hD = 0,
            mMaxI = 0.25, hMaxI = 0.1;

    //The max speed of the motors
    public double SPEED_PERCENT = 1;

    //The acceptable margin of error in inches and radians
    public final double MAX_TRANSLATION_ERROR = 2, MAX_ROTATION_ERROR = Math.toRadians(5);

    public PathFollowerWrapper(HardwareMap hw, Pose2D startPose, double look)
    {
        localization = new Localization(hw, startPose);
        lookAhead = look;

        xPID = new PID(mP,mI,mD);
        yPID = new PID(mP,mI,mD);
        hPID = new PID(hP,hI,hD);
        xPID.capI(mMaxI);
        yPID.capI(mMaxI);
        hPID.capI(hMaxI);
        pidTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    public PathFollowerWrapper(HardwareMap hw, Pose2D startPose, double look, double maxSpeed)
    {
        localization = new Localization(hw, startPose);
        lookAhead=look;

        SPEED_PERCENT = maxSpeed;

        xPID = new PID(mP,mI,mD);
        yPID = new PID(mP,mI,mD);
        hPID = new PID(hP,hI,hD);
        xPID.capI(mMaxI);
        yPID.capI(mMaxI);
        hPID.capI(hMaxI);
        pidTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    /** Initializes a new path to follow */
    public void setPath(Pose2D startPose, Path path){
        follower = new PathFollower(startPose, lookAhead, path);
        pidTimer.reset();
    }

    /** Sets motor powers so drivebase can move towards target based on input (usually from the PathFollower class)*/
    public double[] moveTo(double forward, double strafe, double heading){
        //Rotates the vector based on robot's heading
        double x = forward * Math.cos(getPose().h) - strafe * Math.sin(getPose().h);
        double y = forward * Math.sin(getPose().h) + strafe * Math.cos(getPose().h);
        double h = heading;

        if(Math.hypot(x,y) < MAX_TRANSLATION_ERROR){ //Stops translational movement, focus on heading
            x = 0;
            y = 0;
        } else { //Minimizes the heading control
            h *= hP;
        }

        //Rescales the vector based on the distance/rotation
        double distance = 2 * Math.hypot(x,y);
        x/= distance;
        y/= distance;

        return new double[]{
                x, y, 0//h //TODO fix heading control
        };
    }

    /** Sets motor powers so drivebase can move towards target using PID (for when the lookahead is shrinking)
     * @param move output from PathFollower class, followPath method
     */
    public double[] moveToPID(Pose2D move, double time){
        double forward = move.x,
                strafe = move.y,
                heading = move.h;

        //Rotates the vector based on robot's heading
        double x = Math.cos(getPose().h) * forward + Math.sin(getPose().h) * strafe;
        double y = Math.cos(getPose().h) * strafe - Math.sin(getPose().h) * forward;

        //Calculates the PID values based on error
        x = xPID.pidCalc(x, 0, time);
        y = yPID.pidCalc(y, 0, time);
        double h = hPID.pidCalc(heading, 0, time);

        return new double[]{
                x, y, 0//h
        };
    }

    public void resetPidI (){
        xPID.resetI();
        yPID.resetI();
        hPID.resetI();
    }

    /** Checks if the target pose is within error margin */
    public boolean targetReached(Pose2D target){
        return Math.hypot(target.x-getPose().x, target.y-getPose().y) <= MAX_TRANSLATION_ERROR &&
                Math.abs(target.h-getPose().h) <= MAX_ROTATION_ERROR;
    }
    public void concludePath(){
        follower = null;
    }

    public PathFollower getFollower(){
        return follower;
    }

    public Pose2D getPose(){
        return localization.currentPosition;
    }
    public String getPoseString () {
        return localization.currentPosition.x + ", " +
                localization.currentPosition.y + ", " +
                localization.currentPosition.h;
    }

    public int getHoriOdom(){
        return localization.getHori();
    }
    public int getVertOdom(){
        return localization.getVert();
    }

    Pose2D m;
    public Pose2D followerValues(){
        return m;
    }

    public int getCurrentWayPoint(){
        return follower.getWayPoint();
    }

    /** Calculates and outputs the movement vector */
    public double[] followPath(){
        if(follower != null) {
            //Checks if target is reached
            if(targetReached(follower.getLastPoint())){
                concludePath();

                //Don't move, at target
                return new double[] {0,0,0};
            }
            //Calculates the movement vector
            m = follower.followPath(getPose());
            //Reshapes vector based on error and rotation
            return moveTo(m.x, m.y, m.h);
        }

        //If there's no path, do not move
        return new double[] {0,0,0};
    }

    /** Calculates and outputs the movement vector using PID */
    public double[] followPathPID(){

        if(follower != null) {
            //Checks if target is reached
            if(targetReached(follower.getLastPoint())){
                concludePath();

                //Don't move, at target
                return new double[] {0,0,0};
            }

            //Calculates the movement vector
            m = follower.followPath(getPose());
            //Reshapes vector based on PID values
            return moveToPID(m, pidTimer.time());
        }

        //If there's no path, do not move
        return new double[] {0,0,0};
    }

    /** Updates the localization of the robot */
    public void updatePose(double angle){
        localization.update(angle);
    }

    @NonNull
    @Override
    public String toString() {
        return "MecDrivebase{" +
                ", localization=" + localization +
                ", mxPID=" + xPID +
                ", myPID=" + yPID +
                ", hPID=" + hPID +
                ", follower=" + follower +
                ", m=" + m +
                '}';
    }
}
