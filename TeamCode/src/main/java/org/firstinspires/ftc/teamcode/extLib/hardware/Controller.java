package org.firstinspires.ftc.teamcode.extLib.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;


/**
 * <p>
 * This class encapsulates the gamepad1 and gamepad2 that the op mode gives to you.
 * Call the update function once per frame at the very beginning of the frame.
 * If update is called more than once per frame, inputs may be missed.
 * If update is not called on some frames, you may receive the same input multiple times.
 * </p>
 * <p>
 * The buttonHeld variables will be true if the button is held down and false otherwise.
 * The buttonPressed variables will be true on the first frame
 * the button is held and then they will switch back to false.
 * The ButtonReleased variables will be true on the first frame
 * the button is not held and then will switch back to false.
 * </p>
 * <p>
 * You may write to the public variables in this class,
 * however it is strongly advised to only read from
 * them as the class was not intended to handle that.
 * </p>
 * <p>
 * The buttonFunctions allow you to bind a function pointer or lambda expression to a button.
 * This can simplify code by reducing the amount of code in a loop
 * and the number of repetitive if statements.
 * ex:
 * <pre>
 * {@code
 * controller1.aFunctions.pressed(lift::upPosition);}
 * </pre>
 * or
 * <pre>
 * {@code
 * controller2.xFunctions.pressed(() -> {
 *     intake.switchState(Intake.INTAKE_BUTTON);
 * });}
 * </pre>
 * </p>
 *
 * @author Alex Prichard
 */
public class Controller {
    private class ButtonFunctions {
        private Runnable onHeld;
        private Runnable onPressed;
        private Runnable onReleased;

        public ButtonFunctions() { onHeld = () -> {}; onPressed = () -> {}; onReleased = () -> {}; }

        public void held(Runnable onHeld) { this.onHeld = onHeld; }
        public void pressed(Runnable onPressed) { this.onPressed = onPressed; }
        public void released(Runnable onReleased) { this.onReleased = onReleased; }

        public void run(boolean held, boolean previous) {
            if (held) onHeld.run();
            if (held && !previous) onPressed.run();
            if (!held && previous) onReleased.run();
        }
    }
    // controls how far down the triggers need to be held to count as pressed
    private static final double TRIGGER_THRESHOLD = 0.1;

    private Gamepad gamepad;

    public boolean
            aHeld = false, bHeld = false, xHeld = false, yHeld = false,
            leftBumperHeld = false, rightBumperHeld = false,
            leftTriggerHeld = false, rightTriggerHeld = false,
            leftStickHeld = false, rightStickHeld = false,
            upHeld = false, downHeld = false, leftHeld = false, rightHeld = false,
            backHeld = false, startHeld = false, rumbling = false,

    aPressed = false, bPressed = false, xPressed = false, yPressed = false,
            leftBumperPressed = false, rightBumperPressed = false,
            leftTriggerPressed = false, rightTriggerPressed = false,
            leftStickPressed = false, rightStickPressed = false,
            upPressed = false, downPressed = false, leftPressed = false, rightPressed = false,
            backPressed = false, startPressed = false, rumblingStarted = false,

    aReleased = false, bReleased = false, xReleased = false, yReleased = false,
            leftBumperReleased = false, rightBumperReleased = false,
            leftTriggerReleased = false, rightTriggerReleased = false,
            leftStickReleased = false, rightStickReleased = false,
            upReleased = false, downReleased = false, leftReleased = false, rightReleased = false,
            backReleased = false, startReleased = false, rumblingStopped = false;

    public ButtonFunctions
            aFunctions = new ButtonFunctions(), bFunctions = new ButtonFunctions(),
            xFunctions = new ButtonFunctions(), yFunctions = new ButtonFunctions(),
            leftBumperFunctions = new ButtonFunctions(), rightBumperFunctions = new ButtonFunctions(),
            leftTriggerFunctions = new ButtonFunctions(), rightTriggerFunctions = new ButtonFunctions(),
            leftStickFunctions = new ButtonFunctions(), rightStickFunctions = new ButtonFunctions(),
            upFunctions = new ButtonFunctions(), downFunctions = new ButtonFunctions(),
            leftFunctions = new ButtonFunctions(), rightFunctions = new ButtonFunctions(),
            backFunctions = new ButtonFunctions(), startFunctions = new ButtonFunctions(),
            rumblingFunctions = new ButtonFunctions();

    public double leftTrigger = 0, rightTrigger = 0;

    public Controller(Gamepad g) {
        gamepad = g;
    }

    public void update() {
        boolean previous = aHeld;
        boolean held = gamepad.a;
        aHeld = held;
        aPressed = held && !previous;
        aReleased = !held && previous;
        aFunctions.run(held, previous);

        previous = bHeld;
        held = gamepad.b;
        bHeld = held;
        bPressed = held && !previous;
        bReleased = !held && previous;
        bFunctions.run(held, previous);

        previous = xHeld;
        held = gamepad.x;
        xHeld = held;
        xPressed = held && !previous;
        xReleased = !held && previous;
        xFunctions.run(held, previous);

        previous = yHeld;
        held = gamepad.y;
        yHeld = held;
        yPressed = held && !previous;
        yReleased = !held && previous;
        yFunctions.run(held, previous);

        previous = leftBumperHeld;
        held = gamepad.left_bumper;
        leftBumperHeld = held;
        leftBumperPressed = held && !previous;
        leftBumperReleased = !held && previous;
        leftBumperFunctions.run(held, previous);

        previous = rightBumperHeld;
        held = gamepad.right_bumper;
        rightBumperHeld = held;
        rightBumperPressed = held && !previous;
        rightBumperReleased = !held && previous;
        rightBumperFunctions.run(held, previous);

        previous = leftTriggerHeld;
        leftTrigger = gamepad.left_trigger;
        held = leftTrigger > TRIGGER_THRESHOLD;
        leftTriggerHeld = held;
        leftTriggerPressed = held && !previous;
        leftTriggerReleased = !held && previous;
        leftTriggerFunctions.run(held, previous);

        previous = rightTriggerHeld;
        rightTrigger = gamepad.right_trigger;
        held = rightTrigger > TRIGGER_THRESHOLD;
        rightTriggerHeld = held;
        rightTriggerPressed = held && !previous;
        rightTriggerReleased = !held && previous;
        rightTriggerFunctions.run(held, previous);

        previous = leftStickHeld;
        held = gamepad.left_stick_button;
        leftStickHeld = held;
        leftStickPressed = held && !previous;
        leftStickReleased = !held && previous;
        leftStickFunctions.run(held, previous);

        previous = rightStickHeld;
        held = gamepad.right_stick_button;
        rightStickHeld = held;
        rightStickPressed = held && !previous;
        rightStickReleased = !held && previous;
        rightStickFunctions.run(held, previous);

        previous = upHeld;
        held = gamepad.dpad_up;
        upHeld = held;
        upPressed = held && !previous;
        upReleased = !held && previous;
        upFunctions.run(held, previous);

        previous = downHeld;
        held = gamepad.dpad_down;
        downHeld = held;
        downPressed = held && !previous;
        downReleased = !held && previous;
        downFunctions.run(held, previous);

        previous = leftHeld;
        held = gamepad.dpad_left;
        leftHeld = held;
        leftPressed = held && !previous;
        leftReleased = !held && previous;
        leftFunctions.run(held, previous);

        previous = rightHeld;
        held = gamepad.dpad_right;
        rightHeld = held;
        rightPressed = held && !previous;
        rightReleased = !held && previous;
        rightFunctions.run(held, previous);

        previous = backHeld;
        held = gamepad.back;
        backHeld = held;
        backPressed = held && !previous;
        backReleased = !held && previous;
        backFunctions.run(held, previous);

        previous = startHeld;
        held = gamepad.start;
        startHeld = held;
        startPressed = held && !previous;
        startReleased = !held && previous;
        startFunctions.run(held, previous);

        previous = rumbling;
        held = gamepad.isRumbling();
        rumbling = held;
        rumblingStarted = held && !previous;
        rumblingStopped = !held && previous;
        rumblingFunctions.run(held, previous);
    }
    // the controllers have two rumble motors in them; one in the left, one in the right
    public void rumble(double left, double right, int duration) {
        gamepad.rumble(left, right, duration);
    }

    public void rumble(double left, double right) {
        gamepad.rumble(left, right, Gamepad.RUMBLE_DURATION_CONTINUOUS);
    }

    public void rumble(double power, int duration) {
        gamepad.rumble(power, power, duration);
    }

    public void rumble(double power) {
        gamepad.rumble(power, power, Gamepad.RUMBLE_DURATION_CONTINUOUS);
    }

    public void rumble(int duration) {
        gamepad.rumble(duration);
    }

    public void rumble() {
        gamepad.rumble(Gamepad.RUMBLE_DURATION_CONTINUOUS);
    }

    public void stopRumble() {
        gamepad.stopRumble();
    }

    public void rumble(Gamepad.RumbleEffect rumbleEffect) {
        gamepad.runRumbleEffect(rumbleEffect);
    }

    public void rumbleBlips(int blips) {
        gamepad.rumbleBlips(blips);
    }

    public boolean isRumbling() {
        return rumbling;
    }

    public boolean notRumbling() {
        return !rumbling;
    }
}