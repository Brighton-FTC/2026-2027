package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class DrivetrainTeleOp extends OpMode {
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

    private IMU imu;


    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        imu.initialize(parameters);
    }

    @Override
    public void loop(){
        double ly = -gamepad1.left_stick_y;
        double lx = gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_y;
        if (gamepad1.options) {
            imu.resetYaw();
        }
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = lx * Math.cos(-botHeading) - ly * Math.sin(-botHeading);
        double rotY = lx * Math.sin(-botHeading) + ly * Math.cos(-botHeading);
        rotX = rotX * 1.1;
        double denominator = Math.max(Math.abs(ly) + Math.abs(lx) + Math.abs(rx), 1);


        fl.setPower((rotY + rotX + rx) / denominator);
        fr.setPower((rotY - rotX + rx) / denominator);
        bl.setPower((rotY + rotX - rx) / denominator);
        br.setPower((rotY - rotX - rx) / denominator);

    }

}
