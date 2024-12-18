package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp
public class PreSetLiftTest extends OpMode {

    private boolean dPadAlreadyPressed;
    private int dPadTotalState = -1;
    private int dPadActuallyUsed = dPadTotalState % 5;
    private DcMotorEx v1;
    private DcMotorEx v2;

    @Override
    public void init() {
        v1 = hardwareMap.get(DcMotorEx.class, "v1");
        v2 = hardwareMap.get(DcMotorEx.class, "v2");

        v2.setDirection(DcMotorSimple.Direction.FORWARD);
        v1.setDirection(DcMotorSimple.Direction.REVERSE);

        v1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        v2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        v1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        v2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up && !dPadAlreadyPressed) {
            ++dPadTotalState;
        }
        if (gamepad1.dpad_up && !dPadAlreadyPressed){
            if (dPadActuallyUsed == 0){

            }
        }
        dPadAlreadyPressed = gamepad1.dpad_up;
    }
}
