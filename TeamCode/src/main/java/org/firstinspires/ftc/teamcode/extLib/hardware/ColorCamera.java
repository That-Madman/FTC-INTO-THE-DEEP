package org.firstinspires.ftc.teamcode.extLib.hardware;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.RotatedRect;

import java.util.List;

public class ColorCamera {
    private ColorBlobLocatorProcessor colorLocator;
    private VisionPortal portal;

    public ColorCamera(HardwareMap hardwareMap){
        colorLocator = new ColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(ColorRange.YELLOW)         // use a predefined color match
                .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)    // exclude blobs inside blobs
                .setRoi(ImageRegion.asUnityCenterCoordinates(-1, 1, 1, -1))  // search central 1/4 of camera view
                .setDrawContours(true)                        // Show contours on the Stream Preview
                .setBlurSize(5)                               // Smooth the transitions between different colors in image
                .build();

        portal = new VisionPortal.Builder()
                .addProcessor(colorLocator)
                .setCameraResolution(new Size(640, 480))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();


    }

    public List<ColorBlobLocatorProcessor.Blob> getBlobs(){
        List<ColorBlobLocatorProcessor.Blob> b = colorLocator.getBlobs();
        ColorBlobLocatorProcessor.Util.filterByArea(50, 20000, b);
        return b;
    }

    public double getRightMostPos(){
        double x = -1;

        for(ColorBlobLocatorProcessor.Blob b : getBlobs())
        {
            RotatedRect boxFit = b.getBoxFit();
            if(x < boxFit.center.x)
                x = boxFit.center.x;
        }

        return x;
    }
}
