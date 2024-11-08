package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Board;
import org.firstinspires.ftc.teamcode.Utils;
import org.firstinspires.ftc.teamcode.Vector2d;

@Autonomous(name = "Diff Swerve Test Auto", group = "Linear Opmode")

public class TestAuto extends LinearOpMode {
    Board board;

    public void runOpMode() {
        board = new Board(this);
        board.initIMU();

        //simple sequence to demonstrate the three main autonomous primitives

        //rotate modules to face to the right
        board.driveController.rotateModules(Utils.vRIGHT, false,4000, this);

        //drive 20 cm to the right (while facing forward)
        board.driveController.drive(Utils.vRIGHT, 20, 1, this);

        //turn to face robot right
        board.driveController.rotateRobot(Utils.aRIGHT, this);
    }
}
