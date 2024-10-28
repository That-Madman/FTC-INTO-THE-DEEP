package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MainTele extends OpMode {
    Board board = null;

    @Override
    public void init () {
        board = new Board(hardwareMap);
    }

    @Override
    public void loop () {
    }
}
