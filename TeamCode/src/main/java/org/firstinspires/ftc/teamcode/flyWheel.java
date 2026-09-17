package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

public class flyWheel {

    private DcMotorEx motor;

    public flyWheel (HardwareMap hw) {
        motor = hw.get(DcMotorEx.class, "fW");
    }

    public void setPower (double power) {
        motor.setPower(power);
    }

}