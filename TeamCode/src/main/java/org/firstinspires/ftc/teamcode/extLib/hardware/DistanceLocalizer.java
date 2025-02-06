package org.firstinspires.ftc.teamcode.extLib.hardware;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Wheelie.Pose2D;

public class DistanceLocalizer {
    private AverageDistanceSensor behindSensor, leftSensor, rightSensor;
    private Board board;

    private double startX, startY;
    private Pose2D startPose;
    private Pose2D lastPose;

    public DistanceLocalizer(HardwareMap hw, Board b){
        behindSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"behind"), DistanceUnit.INCH, 25);
        leftSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"left"), DistanceUnit.INCH, 25);
        rightSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"right"), DistanceUnit.INCH, 25);

        board = b;

        this.startPose = new Pose2D(0,0);
        this.lastPose = new Pose2D(0,0);
    }
    public DistanceLocalizer(HardwareMap hw, Board b, Pose2D start){
        behindSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"behind"), DistanceUnit.INCH, 25);
        leftSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"left"), DistanceUnit.INCH, 25);
        rightSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"right"), DistanceUnit.INCH, 25);

        board = b;

        startPose = start;
        lastPose = start;
    }

    public void setStartValues(boolean right){
        startX = behindSensor.getDistance();
        double h = Math.toRadians(board.getDeg()),
            y1 = rightSensor.getDistance(),
            y2 = leftSensor.getDistance(),
            x = behindSensor.getDistance();
        Pose2D pose1 = new Pose2D(
                (x*Math.cos(h) + y1*Math.sin(h)) - startX + startPose.x,
                (x*Math.sin(h) + y1*Math.cos(h)) - startY + startPose.y,
                h
        ),pose2 = new Pose2D(
                (x*Math.cos(h) + y2*Math.sin(h)) - startX + startPose.x,
                (x*Math.sin(h) + y2*Math.cos(h)) - startY + startPose.y,
                h
        );

        double distance1=Math.hypot(pose1.x-lastPose.x, pose1.y-lastPose.y),
                distance2 =Math.hypot(pose2.x-lastPose.x, pose2.y-lastPose.y);
        if(distance2 > distance1)
            startY = y1;
        else startY = y2;
    }

    public Pose2D getPosition(boolean right){
        double h = Math.toRadians(board.getDeg()),
                y1 = rightSensor.getDistance(),
                y2 = leftSensor.getDistance(),
                x = behindSensor.getDistance();
        Pose2D pose1 = new Pose2D(
                (x*Math.cos(h) + y1*Math.sin(h)) - startX + startPose.x,
                (x*Math.sin(h) + y1*Math.cos(h)) - startY + startPose.y,
                h
        ),pose2 = new Pose2D(
                (x*Math.cos(h) + y2*Math.sin(h)) - startX + startPose.x,
                (x*Math.sin(h) + y2*Math.cos(h)) - startY + startPose.y,
                h
        );

        double distance1=Math.hypot(pose1.x-lastPose.x, pose1.y-lastPose.y),
                distance2 =Math.hypot(pose2.x-lastPose.x, pose2.y-lastPose.y);
        if(distance2 > distance1)
            return pose1;
        else return pose2;
    }

    public double getBack(){
        return behindSensor.getDistance();
    }
    public double getLeft(){
        return leftSensor.getDistance();
    }
    public double getRight(){
        return rightSensor.getDistance();
    }

    public void update(){
        behindSensor.update();
        leftSensor.update();
        rightSensor.update();
    }
}
