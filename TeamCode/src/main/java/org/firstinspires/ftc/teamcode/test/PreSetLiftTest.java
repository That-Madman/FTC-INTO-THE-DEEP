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
    private boolean dPadAlreadyPressed2;
    private int dPadTotalState = -1;

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
        int dPadActuallyUsed = dPadTotalState % 5;

        if (gamepad1.dpad_up && !dPadAlreadyPressed) {
            ++dPadTotalState;
            if (dPadActuallyUsed == 0) {
                setLift(0);
            }
            else if (dPadActuallyUsed == 1) {
                setLift(600);
            }
            else if (dPadActuallyUsed == 2) {
                setLift(1200);
            }
            else if (dPadActuallyUsed == 3) {
                setLift(1300);
            }
            else if (dPadActuallyUsed == 4) {
                setLift(2600);
            }
        }

        if (gamepad2.dpad_down && !dPadAlreadyPressed2) {
            dPadTotalState = 0;
            setLift(0);
        }
        dPadAlreadyPressed = gamepad1.dpad_up;
        dPadAlreadyPressed2 = gamepad2.dpad_down;
    }

    public void setLift (int height) {
        v1.setTargetPosition(height);
        v2.setTargetPosition(height);
    }
}
