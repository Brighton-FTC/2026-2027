package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class DrivetrainTeleOp extends OpMode {
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;


    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
    }

    @Override
    public void loop(){
        double ly = -gamepad1.left_stick_y;
        double lx = gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_y;

        double denominator = Math.max(Math.abs(ly) + Math.abs(lx) + Math.abs(rx), 1);
        fl.setPower((ly + lx + rx) / denominator);
        fr.setPower((ly - lx + rx) / denominator);
        bl.setPower((ly + lx - rx) / denominator);
        br.setPower((ly - lx - rx) / denominator);

    }

}
