package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.Board;

@TeleOp (group = "Test")
public class MotorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Board b = new Board(hardwareMap);

        waitForStart();
        while(opModeIsActive()){
            //Code = reality
            // FR = BR
            //
            if(gamepad1.a){
                b.setPowers(1,0,0, 0);
            }

            if(gamepad1.b){
                b.setPowers(0,1,0, 0);
            }

            if(gamepad1.x){
                b.setPowers(0,0,1, 0);
            }

            if(gamepad1.y){
                b.setPowers(0,0,0, 1);
            }
        }
    }
}
