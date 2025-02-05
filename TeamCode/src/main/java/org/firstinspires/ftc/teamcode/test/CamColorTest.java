package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.extLib.hardware.ColorCamera;
import org.firstinspires.ftc.teamcode.extLib.hardware.Controller;

public class CamColorTest extends OpMode {
    private ColorCamera cam;
    private final DcMotor[] base = {null, null, null, null};
    private Controller con;

    @Override
    public void init() {
        cam = new ColorCamera(hardwareMap);

        base[0] = hardwareMap.get(DcMotor.class, "fl");
        base[1] = hardwareMap.get(DcMotor.class, "fr");
        base[2] = hardwareMap.get(DcMotor.class, "br");
        base[3] = hardwareMap.get(DcMotor.class, "bl");

        base[0].setDirection(DcMotorSimple.Direction.REVERSE);
        base[1].setDirection(DcMotorSimple.Direction.FORWARD);
        base[2].setDirection(DcMotorSimple.Direction.FORWARD);
        base[3].setDirection(DcMotorSimple.Direction.REVERSE);

        base[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[1].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[2].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        base[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        base[3].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private boolean runningToYellow = false;
    @Override
    public void loop() {
        telemetry.addData("Running to color?", runningToYellow);

        con.update();
        if(runningToYellow){
            double x = cam.getClosetX();
            if(Math.abs(x) <= 50 || x==-1)
                runningToYellow = false;
            telemetry.addData("Current x offset", x);

            drive(0, Range.clip(x,-1,1), 0, .2);
        }
        if(con.aPressed)
            runningToYellow = !runningToYellow;


        telemetry.update();
    }
    public void drive(double forward, double right, double rotate, double dampen) {
        final double flp = (forward + right + rotate) * dampen;
        final double frp = (forward - right - rotate) * dampen;
        final double blp = (forward - right + rotate) * dampen;
        final double brp = (forward + right - rotate) * dampen;

        setPowers(flp, frp, blp, brp);
    }
    public void setPowers(double flp, double frp, double blp, double brp) {
        double maxSpeed = 1;

        maxSpeed = Math.max(maxSpeed, Math.abs(flp));
        maxSpeed = Math.max(maxSpeed, Math.abs(frp));
        maxSpeed = Math.max(maxSpeed, Math.abs(brp));
        maxSpeed = Math.max(maxSpeed, Math.abs(blp));

        flp /= maxSpeed;
        frp /= maxSpeed;
        brp /= maxSpeed;
        blp /= maxSpeed;

        base[0].setPower(flp);
        base[1].setPower(frp);
        base[2].setPower(brp);
        base[3].setPower(blp);
    }
}
