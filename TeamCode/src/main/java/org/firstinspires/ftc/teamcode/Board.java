package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class Board {
   DcMotorEx[] drivers = {null, null , null, null};
   DcMotorEx[] encoders = {null, null};
   IMU imu = null;

   public Board (HardwareMap hwMap) {
      for (int i = 0; 4 > i; ++i) {
         //Is this more efficient? I don't know. But I like it, and Java optimization is already a fool's errand.
         drivers[i] = hwMap.get(DcMotorEx.class, (new String[]{"r1", "r2", "l1", "l2"})[i]);
      }

      encoders[0] = hwMap.get(DcMotorEx.class, "horz");
      encoders[1] = hwMap.get(DcMotorEx.class, "vert");

      for (DcMotorEx enc : encoders) {
         enc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         enc.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      }

      imu = hwMap.get(IMU.class, "imu");
   }

   public void drive (double x, double y, double right) {
   }
}
