package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "Tests")
public class PreSetLiftTest extends OpMode {

    private boolean dPadAlreadyPressed;
    private boolean dPadAlreadyPressed2;
    private boolean dPadAlreadyPressed3;
    private int dPadTotalState = -1;
    int dPadActuallyUsed;

    private DcMotorEx v1;
    private DcMotorEx v2;

    public void setLift (int height) {
        v1.setTargetPosition(height);
        v2.setTargetPosition(height);
    }





    @Override
    public void init() {
        v1 = hardwareMap.get(DcMotorEx.class, "v1");
        v2 = hardwareMap.get(DcMotorEx.class, "v2");

        v1.setDirection(DcMotorSimple.Direction.REVERSE);
        v2.setDirection(DcMotorSimple.Direction.FORWARD);

        v1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v1.setPower(1);
        v1.setTargetPosition(0);
        v1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        v2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v2.setPower(1);
        v2.setTargetPosition(0);
        v2.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }

    @Override
    public void loop() {
        dPadActuallyUsed = dPadTotalState % 3;


        if (gamepad1.dpad_up && !dPadAlreadyPressed) {
            ++dPadTotalState;
}
            switch (dPadActuallyUsed) {
                case 0:
                    setLift(0);
                    telemetry.addLine("I should be doing something");
                    break;
                case 1:
                    setLift(850);
                    break;
                case 2:
                    setLift(2700);
                    break;
            }


        if (gamepad1.dpad_down && !dPadAlreadyPressed2) {
            dPadTotalState = 0;
            setLift(0);
        }

        if (gamepad1.dpad_left && !dPadAlreadyPressed3) {
            dPadTotalState = 2;
            setLift(2700);
        }
        dPadAlreadyPressed = gamepad1.dpad_up;
        dPadAlreadyPressed2 = gamepad1.dpad_down;
        dPadAlreadyPressed3 = gamepad1.dpad_left;

        telemetry.addData("Dpad Total Value", dPadTotalState);
        telemetry.addData("Dpad Used Value", dPadActuallyUsed);

    }



}
