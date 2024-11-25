package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import java.util.List;
@TeleOp
public class LimelightOpMode extends OpMode{
    Limelight3A limelight3A;
    @Override
    public void init() {
        limelight3A = hardwareMap.get(Limelight3A.class, "lime");
    }
    public void start(){
        limelight3A.pipelineSwitch(0);
        limelight3A.start();
    }
    @Override
    public void loop() {
        LLResult llResult = limelight3A.getLatestResult();
        if(llResult != null && llResult.isValid()) {
            telemetry.addData("Tx", llResult.getTx());
            telemetry.addData("Ty", llResult.getTy());
            telemetry.addData("Ta", llResult.getTa());
            telemetry.addData("Corner Coords", llResult.getColorResults());

        }
        else{
            telemetry.addLine("WHERE ARE THEY? WHERE ARE THE SAMPLES? AAAAAAAASAaAAAAAAAAAAAAAAAAAAAAAAAA");
        }
    }
}
