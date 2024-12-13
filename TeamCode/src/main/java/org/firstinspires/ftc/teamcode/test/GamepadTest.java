package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (group = "Tests")
public class GamepadTest extends OpMode {
    boolean aHeld;
    boolean bHeld;
    boolean xHeld;
    boolean yHeld;

    int aP;
    int bP;
    int xP;
    int yP;

    @Override
    public void init() {
    }

    public void loop () {
            if (!aHeld && gamepad1.a) {
                ++aP;
            }

            if (!bHeld && gamepad1.b) {
                ++bP;
            }

            if (!xHeld && gamepad1.x) {
                ++xP;
            }

            if (!yHeld && gamepad1.y) {
                ++yP;
            }

            telemetry.addData("a?", gamepad1.a);
            telemetry.addData("a held?", aHeld);
            telemetry.addData("aP?", aP);

            telemetry.addData("b?", gamepad1.b);
            telemetry.addData("b held?", bHeld);
            telemetry.addData("bP?", bP);

            telemetry.addData("x?", gamepad1.x);
            telemetry.addData("x held?", xHeld);
            telemetry.addData("xP?", xP);

            telemetry.addData("y?", gamepad1.y);
            telemetry.addData("y held?", yHeld);
            telemetry.addData("yP?", yP);


            aHeld = gamepad1.a;
            bHeld = gamepad1.b;
            xHeld = gamepad1.x;
            yHeld = gamepad1.y;
    }
}
