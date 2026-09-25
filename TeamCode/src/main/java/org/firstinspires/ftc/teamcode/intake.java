package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake {
    public DcMotorEx motor;


    public intake(HardwareMap hw) {
        motor = hw.get(DcMotorEx.class, "int");
    }

    public void setMotor(double power) {
        motor.setPower(power);
    }

}
