package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp
public class LimelightOpMode extends OpMode{
    int tag;
    byte index;
    boolean aAlreadyPressed;


    private void GoAfterTag(int tag) {
        limelight3A.pipelineSwitch(tag -10);
    }
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
        if (gamepad1.a && !aAlreadyPressed){
            ++index;
            index %= 6;
        }
        switch(index) {
            case 0:
                GoAfterTag(11);
                tag = 11;
                break;
            case 1:
                GoAfterTag(12);
                tag = 12;
                break;
            case 2:
                GoAfterTag(13);
                tag = 13;
                break;
            case 3:
                GoAfterTag(14);
                tag = 14;
                break;
            case 4:
                GoAfterTag(15);
                tag = 15;
                break;
            case 5:
                GoAfterTag(16);
                tag = 16;
                break;
        }

        LLResult llResult = limelight3A.getLatestResult();
        if(llResult != null && llResult.isValid()) {
            telemetry.addData("I see AprilTag ", tag);
        }
        else{
            telemetry.addLine("I do not see any AprilTags");
        }
        aAlreadyPressed = gamepad1.a;
    }
}
