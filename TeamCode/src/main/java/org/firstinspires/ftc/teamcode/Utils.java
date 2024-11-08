package org.firstinspires.ftc.teamcode;

public final class Utils {
    //relative to robot starting position (right = east, left = west, forward = north, backward = south)
    public static final Angle
            RIGHT = new Angle(90, Angle.AngleType.NEG_180_TO_180_HEADING),
            LEFT = new Angle(-90, Angle.AngleType.NEG_180_TO_180_HEADING),
            BACKWARD = new Angle(180, Angle.AngleType.NEG_180_TO_180_HEADING),
            FORWARD = new Angle(0, Angle.AngleType.NEG_180_TO_180_HEADING);

    private Utils () {}

    public static double scaleVal (double input, double minInputVal, double maxInputVal, double minOutputVal, double maxOutputVal) {
        if (input > maxInputVal) input = maxInputVal;
        double inputRange = Math.abs(maxInputVal - minInputVal);
        double outputRange = Math.abs(maxOutputVal - minOutputVal);
        double scaleFactor = input/inputRange;
        return outputRange * scaleFactor + minOutputVal;
    }

    //returns python version of n % m (n is dividend, m is divisor)
    //python % never returns negative numbers, but Java % does
    public static double mod (double n, double m) {
        return (((n % m) + m) % m);
    }
}
