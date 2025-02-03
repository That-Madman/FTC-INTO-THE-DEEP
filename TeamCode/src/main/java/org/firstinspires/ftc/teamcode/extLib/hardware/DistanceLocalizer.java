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

    public DistanceLocalizer(HardwareMap hw, Board b){
        behindSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"behind"), DistanceUnit.INCH, 25);
        leftSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"left"), DistanceUnit.INCH, 25);
        rightSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"right"), DistanceUnit.INCH, 25);

        board = b;

        this.startPose = new Pose2D(0,0);
    }
    public DistanceLocalizer(HardwareMap hw, Board b, Pose2D start){
        behindSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"behind"), DistanceUnit.INCH, 25);
        leftSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"left"), DistanceUnit.INCH, 25);
        rightSensor = new AverageDistanceSensor(hw.get(DistanceSensor.class,"right"), DistanceUnit.INCH, 25);

        board = b;

        startPose = start;
    }

    public void setStartValues(boolean right){
        startX = behindSensor.getDistance();
        startY = right ? rightSensor.getDistance() : leftSensor.getDistance();
    }

    public Pose2D getPosition(boolean right){
        double h = Math.toRadians(board.getDeg()),
                y = right ? rightSensor.getDistance() : leftSensor.getDistance(),
                x = behindSensor.getDistance();
        return new Pose2D(
                (x*Math.cos(h) + y*Math.sin(h)) - startX + startPose.x,
                (x*Math.sin(h) + y*Math.cos(h)) - startY + startPose.y,
                h
        );
    }

    public void update(){
        behindSensor.update();
        leftSensor.update();
        rightSensor.update();
    }
}
