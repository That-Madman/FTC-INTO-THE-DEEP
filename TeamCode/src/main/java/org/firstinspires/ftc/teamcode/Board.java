package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class Board {
   DcMotorEx[] drivers = {null, null , null, null};
   IMU imu = null;

   public Board (HardwareMap hwMap) {
      for (int i = 0; 4 > i; ++i) {
         drivers[i] = hwMap.get(DcMotorEx.class, (new String[]{"r1", "r2", "l1", "l2"})[i]);
      }
      imu = hwMap.get(IMU.class, "imu");
   }
}
