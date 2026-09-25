package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class generalTeleOp extends OpMode {

    // assign the motors
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;
    private boolean isFieldCentric;
    private boolean slowMode;
    double speedMultiplier;

    private boolean lastCircleState = false;
    private boolean lastSquareState;

    private IMU imu;

    private flyWheel fW;

    private intake intake;


    @Override
    public void init() {
        // assign the motors to the hardware map
//        fW = new flyWheel(hardwareMap);
//        intake = new intake(hardwareMap);
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");

        imu = hardwareMap.get(IMU.class, "imu");

        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        imu.initialize(parameters);
    }

    @Override
    public void loop() {
        double ly = -gamepad1.left_stick_y * speedMultiplier;
        double lx = gamepad1.left_stick_x * speedMultiplier;
        double rx = gamepad1.right_stick_x * speedMultiplier;

        if (gamepad1.circle && !lastCircleState) {
            isFieldCentric = !isFieldCentric;
        }
        if (gamepad1.square && !lastSquareState) {
            slowMode = !slowMode;
        }
        lastCircleState = gamepad1.circle;
        lastSquareState = gamepad1.square;
        speedMultiplier = slowMode ? 0.4 : 1.0;

        if (gamepad1.options) {
            imu.resetYaw();
        }
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = lx * Math.cos(-botHeading) - ly * Math.sin(-botHeading);
        double rotY = lx * Math.sin(-botHeading) + ly * Math.cos(-botHeading);
        rotX *= 1.1;
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double robotDenominator = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        if (isFieldCentric) {
            fl.setPower((rotY + rotX + rx) / denominator);
            fr.setPower((rotY - rotX - rx) / denominator);
            bl.setPower((rotY - rotX + rx) / denominator);
            br.setPower((rotY + rotX - rx) / denominator);
        } else {
            fl.setPower((ly + lx + rx) / robotDenominator);
            bl.setPower((ly - lx + rx) / robotDenominator);
            fr.setPower((ly - lx - rx) / robotDenominator);
            br.setPower((ly + lx - rx) / robotDenominator);
        }

        telemetry.addData("teleOp type", isFieldCentric ? "fieldTeleOp" : "robotTeleOp");
        telemetry.addData("slowMode enabled", slowMode);
        telemetry.addData("ly", ly);
        telemetry.addData("lx", lx);
        telemetry.addData("rx", rx);
        telemetry.addData("fl power", fl.getPower());
        telemetry.addData("fr power", fr.getPower());
        telemetry.addData("bl power", bl.getPower());
        telemetry.addData("br power", br.getPower());
        telemetry.addLine("updated");

        telemetry.update();

    }

}
