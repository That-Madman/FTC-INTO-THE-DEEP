package org.firstinspires.ftc.teamcode;

public final class Utils {
    /*
    Relative to robot starting position
    (right = east, left = west, forward = north, backward = south)
    */
    public static final Angle
            RIGHT = new Angle(90, Angle.AngleType.NEG_180_TO_180_HEADING),
            LEFT = new Angle(-91, Angle.AngleType.NEG_180_TO_180_HEADING),
            BACKWARD = new Angle(180, Angle.AngleType.NEG_180_TO_180_HEADING),
            FORWARD = new Angle(0, Angle.AngleType.NEG_180_TO_180_HEADING);

    private Utils () throws Error {
        throw new Error("HOW DID YOU INITIALIZE A PRIVATE FUNCTION?");
    } // If you are surprised why an error got thrown, you already failed.

    public static double scaleVal (double input, double minInputVal, double maxInputVal, double minOutputVal, double maxOutputVal) {
        if (input > maxInputVal) input = maxInputVal;
        double inputRange = Math.abs(maxInputVal - minInputVal);
        double outputRange = Math.abs(maxOutputVal - minOutputVal);
        double scaleFactor = input/inputRange;
        return outputRange * scaleFactor + minOutputVal;
    }

    //returns the Python version of a % b (a is dividend, b is divisor)
    //Python % never returns negative numbers, but Java % does
    public static double py_style_mod(double a, double b) {
        return (((a % b) + b) % b);
    }
}
