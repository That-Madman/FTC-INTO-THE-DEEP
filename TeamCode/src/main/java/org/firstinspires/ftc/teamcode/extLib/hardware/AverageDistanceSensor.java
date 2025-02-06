package org.firstinspires.ftc.teamcode.extLib.hardware;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.Arrays;

public class AverageDistanceSensor {
    private DistanceSensor distanceSensor;
    private DistanceUnit unit;

    private double[] distances;
    private int curIndex = 0;
    private final double MAX_DEVIATION = 24.0; // Ignore sudden changes larger than 24 inches

    public AverageDistanceSensor(DistanceSensor sensor, DistanceUnit u, int savedDistances) {
        distanceSensor = sensor;
        unit = u;
        distances = new double[savedDistances];
        Arrays.fill(distances, sensor.getDistance(unit)); // Initialize with first value
    }

    public double getDistance() {
        return Arrays.stream(distances).average().orElse(Double.NaN);
    }

    public void update() {
        double newDistance = distanceSensor.getDistance(unit);

        // Ignore readings that deviate too much from the average (likely interference)
        double avg = getDistance();
        if (Math.abs(newDistance - avg) > MAX_DEVIATION) {
            return; // Skip this update to prevent bad data from affecting localization
        }

        distances[curIndex] = newDistance;
        curIndex = (curIndex + 1) % distances.length;
    }
}
