package org.firstinspires.ftc.teamcode.autonomous.ExperimentalCameraImplementation;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.extLib.autoSystems.WheelOp;
import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

@Autonomous
public class ApriltagDetectionTest extends WheelOp {
    private Board board;
    public void onInit(){}
    public void run(){
        board.genericCameraThingy(this.telemetry);
    }
}
