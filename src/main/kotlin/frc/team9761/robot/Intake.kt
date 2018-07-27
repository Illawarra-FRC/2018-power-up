package frc.team9761.robot

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.Talon
import edu.wpi.first.wpilibj.DoubleSolenoid


class Intake {
    val intakeLeft = Talon(Ports.INTAKE_LEFT_CHANNEL)
    val intakeRight = Talon(Ports.INTAKE_RIGHT_CHANNEL)
    val cylinder = DoubleSolenoid(Ports.SOLENOID_FORWARD_CHANNEL, Ports.SOLENOID_REVERSE_CHANNEL)
    fun grab() {
        setIntakePower(Speeds.IN_POWER)
    }

    fun eject() {
        setIntakePower(Speeds.OUT_POWER)
    }
    fun open(){
        cylinder.set(DoubleSolenoid.Value.kForward)
    }
    fun close(){
        cylinder.set(DoubleSolenoid.Value.kReverse)
    }

    fun stop() {
        setIntakePower(0.0)
    }

    fun setIntakePower(intakePower: Double) {
        SmartDashboard.putNumber("intakePower", intakePower)
        intakeLeft.set(intakePower)
        intakeRight.set(-intakePower)
    }
}
