package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class robotTeleOp extends OpMode {

    private DcMotor fl;
    private DcMotor bl;
    private DcMotor fr;
    private DcMotor br;

    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

    }


    @Override
    public void loop(){
        double lx = gamepad1.left_stick_x;
        double ly = gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        fl.setPower((ly + lx + rx) / denominator);
        bl.setPower((ly - lx + rx) / denominator);
        fr.setPower((ly - lx - rx) / denominator);
        br.setPower((ly + lx - rx) / denominator);


        telemetry.addData("ly", ly);
        telemetry.addData("lx", lx);
        telemetry.addData("rx", rx);
        telemetry.addData("fl power", fl.getPower());
        telemetry.addLine("updated");

        telemetry.update();
    }
}
