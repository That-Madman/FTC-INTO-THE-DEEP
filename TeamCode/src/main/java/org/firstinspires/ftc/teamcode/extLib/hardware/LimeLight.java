package org.firstinspires.ftc.teamcode.extLib.hardware;

import static java.lang.Math.abs;
import static java.lang.Math.atan;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimeLight {
    final Limelight3A lime;

    LimeLight (HardwareMap hwMap) {
        lime = hwMap.get(Limelight3A.class, "lime");
    }

    public static double innerAngle (double x1, double y1, double x2, double y2) {
        return atan (abs(y1 - y2) / abs(x1 - x2));
    }
}
