package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extLib.hardware.Board;

@TeleOp(name = "The one you use")
public class MainTele extends OpMode {
    private boolean driveFieldRel = true;
    private boolean resetImu;
    private boolean closed;
    private byte rot;
    private boolean bigClosed;
    private int height;

    private int dPadState;
    private boolean specimenMode;

    private boolean a1Held;
    private boolean b1Held;
    private boolean x1Held;
    private boolean y1Held;
    private boolean a2Held;
    private boolean b2Held;
    private boolean x2Held;
    private boolean y2Held;
    private boolean rB2Held;
    private boolean up2Held;
    private boolean down2Held;
    private boolean left2Held;
    private boolean down1Held;

    private byte sState = 1;
    private byte re;

    private Board board;

    @Override
    public void init () {
        board = new Board(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.left_bumper) {
            if (driveFieldRel) {
                board.driveFieldRelative(
                        -gamepad1.left_stick_y * 0.5,
                        gamepad1.left_stick_x * 0.5,
                        gamepad1.right_stick_x * 0.5
                );
                telemetry.addData("Driving", "Field Relative");
            } else {
                board.drive(
                        -gamepad1.left_stick_y * 0.5,
                        gamepad1.left_stick_x * 0.5,
                        gamepad1.right_stick_x * 0.5
                );
                telemetry.addData("Driving", "Robot Relative");
            }
        } else {
            if (driveFieldRel) {
                board.driveFieldRelative(
                        -gamepad1.left_stick_y,
                        gamepad1.left_stick_x,
                        gamepad1.right_stick_x
                );
                telemetry.addData("Driving", "Field Relative");
            } else {
                board.drive(
                        -gamepad1.left_stick_y,
                        gamepad1.left_stick_x,
                        gamepad1.right_stick_x
                );
                telemetry.addData("Driving", "Robot Relative");
            }
        }

        height += (int) ((gamepad2.left_trigger - gamepad2.right_trigger) * 10);

        height = Math.max(height, 0);
        height = Math.min(height, 2800);

        board.setLift(height);

        if (gamepad1.a && !a1Held) {
            driveFieldRel ^= true;
        }

        if (gamepad1.y && !y1Held) {
            board.resetImu();
            resetImu = true;
        }

        if (resetImu) {
            telemetry.addLine("IMU has been reset.");
        }

        if (!a2Held && gamepad2.a) {
            closed ^= true;
        }

        if (!b1Held && gamepad1.b) {
            board.setRot((byte) 0);
            board.setBigGrab(false);
            board.setTinyGrab(false);

            board.setPick(false);
            board.sleep(150, this);

            board.setReach((byte) 1);
            board.sleep(150, this);

            board.setSwivel((byte) 0);
            board.sleep(150, this);

            board.setReach((byte) 0);
            board.sleep(150, this);

            board.setTinyGrab(true);
            board.sleep(150, this);

            board.setPick(true);
            board.sleep(150, this);

            board.setReach((byte) 1);
            board.sleep(150, this);

            board.setBigGrab(true);
            board.sleep(150, this);

            board.setRot((byte) 2);

            re = 1;
            sState = 1;
            closed = true;
            rot = 2;
            bigClosed = true;
        }

        if (gamepad2.b && !b2Held) {
            board.setTinyGrab(false);
            board.setBigGrab(false);

            board.sleep(200, this);

            board.setRot((byte) 0);

            re = 1;
            sState = 1;
            closed = true;
            rot = 0;
            bigClosed = false;
            height = 0;
        }

        if (!x1Held && gamepad1.x) {
            ++re;
            re %= 3;
        }


        if (!x2Held && gamepad2.x) {
            ++sState;
            sState %= 3;
        }

        if (!y2Held && gamepad2.y) {
            bigClosed ^= true;
        }

        if (!rB2Held && gamepad2.right_bumper) {
            rot = (byte) ((0 < rot) ? 0 : 1);
        }

        if (gamepad2.dpad_up && !up2Held) {
            ++dPadState;

            dPadState %= 3;

            switch (dPadState) {
                case 0:
                    height = 0;
                    break;
                case 1:
                    height = 850;
                    break;
                case 2:
                    height = 2700;
                    break;
            }
        } else if (gamepad2.dpad_down && !down2Held){
            dPadState = 0;
            height = 0;
        } else if (gamepad2.dpad_left && !left2Held) {
            dPadState = 2;
            height = 2700;
        }

        if (gamepad1.dpad_down && !down1Held) {
            board.setRot((byte) ((specimenMode) ? 0 : 1));
            specimenMode ^= true;
        }

        board.setReach(re);
        board.setSwivel(sState);
        board.setPick(closed);
        board.setRot(rot);

        board.setTinyGrab(bigClosed);
        board.setBigGrab(bigClosed);

        a1Held = gamepad1.a;
        b1Held = gamepad1.b;
        x1Held = gamepad1.x;
        y1Held = gamepad1.y;
        down1Held = gamepad1.dpad_down;

        a2Held = gamepad2.a;
        b2Held = gamepad2.b;
        x2Held = gamepad2.x;
        y2Held = gamepad2.y;
        rB2Held = gamepad2.right_bumper;
        up2Held = gamepad2.dpad_up;
        down2Held = gamepad2.dpad_down;
        left2Held = gamepad2.dpad_left;
    }
}