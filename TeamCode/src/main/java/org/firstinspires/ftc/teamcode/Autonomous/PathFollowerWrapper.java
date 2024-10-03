package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.PID;

import java.util.Arrays;

import Wheelie.Path;
import Wheelie.PathFollower;
import Wheelie.Pose2D;

public class PathFollowerWrapper {
    private Localization localization;

    private PathFollower follower;
    private double lookAhead;

    //TODO Set start time and cap I
    private PID xPID, yPID, hPID;
    private static final double mP = 1./24., mI = .01, mD = .125,
                                hP = 1./Math.PI, hI = 0, hD = 0,
                                mMaxI = .25, hMaxI = .1;

    //The max speed of the motors
    public static double SPEED_PERCENT = 1;

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
    }

    public PathFollowerWrapper(HardwareMap hw, Pose2D startPose, double look, double maxSpeed)
    {
        localization = new Localization(hw, startPose);
        lookAhead=look;

        SPEED_PERCENT = maxSpeed;

        xPID = new PID(mP,mI,mD);
        yPID = new PID(mP,mI,mD);
        hPID = new PID(hP,hI,hD);
    }

    public void setPath(Pose2D startPose, Path path){
        follower = new PathFollower(startPose, lookAhead, path);
    }
    public PathFollower getFollower(){
        return follower;
    }

    /** Sets motor powers so drivebase can move towards target based on input (usually from the PathFollower class)*/
    public double[] moveTo(double forward, double strafe, double heading){
        double movementAngle = Math.atan2(strafe, forward) - localization.getAngle();
        double x = forward * Math.cos(movementAngle) - strafe * Math.sin(movementAngle);
        double y = forward * Math.sin(movementAngle) + strafe * Math.cos(movementAngle);
        double h = Math.abs(heading) <= Math.toRadians(5) ? 0 : heading * .25;

        double length = Math.hypot(x, y);

        if(length > 1) {
            x /= length;
            y /= length;
            h /= length;
        }

        return new double[] {
                x + y + h,
                x - y + h,
                x + y - h,
                x - y - h};
    }

    /** Sets motor powers so drivebase can move towards target using PID (for when the lookahead is shrinking)
     * @param move output from PathFollwer class, followPath method
     */
    public double[] moveToPID(Pose2D move){
        double forward = move.x,
                strafe = move.y,
                heading = move.h;

        double x = Math.cos(localization.getAngle()) * forward + Math.sin(localization.getAngle()) * strafe;
        double y = Math.cos(localization.getAngle()) * strafe - Math.sin(localization.getAngle()) * forward;

        double movementAngle = Math.atan2(strafe, forward) - localization.getAngle(),
                distance = Math.hypot(x, y);

        x = xPID.pidCalc(distance * Math.cos(movementAngle), 0);
        y = yPID.pidCalc(distance * Math.sin(movementAngle), 0);

        double length = Math.hypot(x,y);

        if(length > 1) {
            x /= length;
            y /= length;
            if(Math.abs(heading) > Math.toRadians(5)){
                x*= .75;
                y *= .75;
            }
        }

        if(length > 5){
            if(heading > Math.toRadians(5))
                heading = Range.clip(hPID.pidCalc(-heading, 0), -.25, .25) * ((x+y)/(2.0 * length));
        }
        else
            heading = Range.clip(hPID.pidCalc(-heading, 0), -.5, .5);

        return new double[] {
                x + y + heading,
                x - y + heading,
                x + y - heading,
                x - y - heading};
    }

    public void resetPID(){
        xPID.resetI();
        yPID.resetI();
        hPID.resetI();
        xPID.setStartTime();
        yPID.setStartTime();
        hPID.setStartTime();
    }

    public boolean targetReached(Pose2D target){
        return Math.hypot(target.x-getPose().x, target.y-getPose().y) <= follower.look;
    }
    public void concludePath(){
        follower = null;
    }

    public Pose2D getPose(){
        return localization.currentPosition;
    }
    public String getPoseString(){
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

    public double[] followPath(){
        localization.update();

        if(follower != null) {
            if(targetReached(follower.getLastPoint())){
                concludePath();
                return new double[] {0,0,0,0};
            }
            m = follower.followPath(getPose());
            return moveTo(m.x, m.y, m.h);
        }

        return new double[] {0,0,0,0};
    }
    public double[] followPathPID(){
        localization.update();

        if(follower != null) {
            if(targetReached(follower.getLastPoint())){
                concludePath();
                return new double[] {0,0,0,0};
            }

            m = follower.followPath(getPose());
            return moveToPID(m);
        }

        return new double[] {0,0,0,0};
    }

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
