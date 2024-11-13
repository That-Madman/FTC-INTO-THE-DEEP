package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.types.Vector2d;
import org.firstinspires.ftc.teamcode.utils.Board;
import org.firstinspires.ftc.teamcode.utils.GenUtils;

@TeleOp (name = "TeleOp")
public class TeleOpMode extends OpMode {
    Board board;

    public boolean willResetIMU = false;

    public void init () {
        board = new Board(this);
    }

    public void init_loop () {
        if (gamepad1.y) {
            willResetIMU = !willResetIMU;
        }
            telemetry.addData("IMU reset?", willResetIMU);
            telemetry.update();
    }
    public void start () {
        if (willResetIMU) {
            board.resetIMU();
        }
    }


    public void loop () {
        Vector2d joystick1 = new Vector2d(gamepad1.left_stick_x, -gamepad1.left_stick_y); //LEFT joystick
        Vector2d joystick2 = new Vector2d(gamepad1.right_stick_x, -gamepad2.right_stick_y); //RIGHT joystick

        board.driveController.updateUsingJoysticks(
                GenUtils.checkDeadband(joystick1),
                GenUtils.checkDeadband(joystick2));


//        //uncomment for live tuning of ROT_ADVANTAGE constant
//        if (gamepad1.b) {
//            robot.driveController.moduleRight.ROT_ADVANTAGE += 0.01;
//            robot.driveController.moduleLeft.ROT_ADVANTAGE += 0.01;
//        }
//        if (gamepad1.x) {
//            robot.driveController.moduleRight.ROT_ADVANTAGE -= 0.01;
//            robot.driveController.moduleLeft.ROT_ADVANTAGE -= 0.01;
//        }
//        telemetry.addData("ROT_ADVANTAGE: ", robot.driveController.moduleLeft.ROT_ADVANTAGE);


        //to confirm that joysticks are operating properly
        telemetry.addData("Joystick 1", joystick1);
        telemetry.addData("Joystick 2", joystick2);

        telemetry.update();
    }
}
