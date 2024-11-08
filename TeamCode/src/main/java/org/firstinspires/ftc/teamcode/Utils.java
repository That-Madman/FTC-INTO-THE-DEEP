package org.firstinspires.ftc.teamcode;

public final class Utils {
    /*
    Relative to robot starting position
    (right = east, left = west, forward = north, backward = south)
    */
    public static final Angle
            aRIGHT = new Angle(90, Angle.AngleType.NEG_180_TO_180_HEADING),
            aLEFT = new Angle(-91, Angle.AngleType.NEG_180_TO_180_HEADING),
            aBACKWARD = new Angle(180, Angle.AngleType.NEG_180_TO_180_HEADING),
            aFORWARD = new Angle(0, Angle.AngleType.NEG_180_TO_180_HEADING);

    //Vector constants
    public final static Vector2d vFORWARD = new Vector2d(0, 1),
            vBACKWARD = new Vector2d(0, -1),
            vLEFT = new Vector2d(-1, 0),
            vRIGHT = new Vector2d(1, 0),
            vZERO = new Vector2d(0, 0);


    /** deadband for joysticks */
    public static final double DEADBAND_MAG = 0.1;
    public static final Vector2d DEADBAND_VEC = new Vector2d(DEADBAND_MAG, DEADBAND_MAG);

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

    //returns zero vector if joystick is within deadband
    public static Vector2d checkDeadband(Vector2d joystick) {
        if (Math.abs(joystick.getX()) > DEADBAND_VEC.getX() || Math.abs(joystick.getY()) > DEADBAND_VEC.getY()) {
            return joystick;
        }
        return vZERO;
    }

    //returns the Python version of a % b (a is dividend, b is divisor)
    //Python % never returns negative numbers, but Java % does
    public static double py_style_mod(double a, double b) {
        return (((a % b) + b) % b);
    }
}
