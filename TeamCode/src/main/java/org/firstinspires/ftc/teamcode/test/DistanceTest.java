package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.extLib.hardware.Board;
import org.firstinspires.ftc.teamcode.extLib.hardware.DistanceLocalizer;

import Wheelie.Pose2D;

@TeleOp
public class DistanceTest extends LinearOpMode {
    private Board board;
    private DistanceLocalizer localizer;

    @Override
    public void runOpMode() throws InterruptedException {
        board = new Board(hardwareMap);
        localizer = new DistanceLocalizer(hardwareMap, board);
        board.resetImu();

        while (opModeInInit()){
            localizer.update();
        }
        localizer.setStartValues(false);

        while(opModeIsActive()){
            boolean useRight = AngleUnit.normalizeDegrees(board.getDeg()) < 0;
            localizer.update();

            telemetry.addData("Sparkfun", toString(board.getCurrentPose()));
            telemetry.addData("Distance Sensors", toString(localizer.getPosition(useRight)));
            telemetry.addData("Distance Sensors", localizer.getBack());
            telemetry.addData("Distance Sensors", localizer.getLeft());
            telemetry.addData("Distance Sensors", localizer.getRight());
            telemetry.addData("Distance Sensors", useRight);
            telemetry.update();
        }
    }

    private String toString(Pose2D p){
        return p.x + ", " + p.y + ", " + Math.toDegrees(p.h);
    }
}
