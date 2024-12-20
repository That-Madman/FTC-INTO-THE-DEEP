package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.extLib.Board;

@TeleOp (group = "Test")
public class MotorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Board b = new Board(hardwareMap, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        b.setPowers(.5,.5, .5, .5);
        while(opModeIsActive()){
            //Code = reality
            // FR = BR
            //
            /*if(gamepad1.a){
                b.setVelocities(1,0,0, 0);
            }

            if(gamepad1.b){
                b.setVelocities(0,1,0, 0);
            }

            if(gamepad1.x){
                b.setVelocities(0,0,1, 0);
            }

            if(gamepad1.y){
                b.setVelocities(0,0,0, 1);
            }*/
            telemetry.addData("motor 0", b.getVelocity(0));
            telemetry.addLine();
            telemetry.addData("motor 1", b.getVelocity(1));
            telemetry.addLine();
            telemetry.addData("motor 2", b.getVelocity(2));
            telemetry.addLine();
            telemetry.addData("motor 3", b.getVelocity(3));
            telemetry.addLine();
            telemetry.update();
        }
    }
}
