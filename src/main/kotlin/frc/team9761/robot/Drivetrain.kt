package frc.team9761.robot

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard

class Drivetrain {
    val leftA = VictorSP(Ports.DRIVE_LEFT_A_CHANNEL)
    val leftB = VictorSP(Ports.DRIVE_LEFT_B_CHANNEL)
    val rightA = VictorSP(Ports.DRIVE_RIGHT_A_CHANNEL)
    val rightB = VictorSP(Ports.DRIVE_RIGHT_B_CHANNEL)

    val leftEncoder = Encoder(Ports.ENCODER_LEFT_A_CHANNEL, Ports.ENCODER_LEFT_B_CHANNEL, true)
    val rightEncoder = Encoder(Ports.ENCODER_RIGHT_A_CHANNEL, Ports.ENCODER_RIGHT_B_CHANNEL) 

    fun init() {
        leftEncoder.reset()
        rightEncoder.reset()    
        leftEncoder.setDistancePerPulse(Speeds.DISTANCE_PER_PULSE)
        rightEncoder.setDistancePerPulse(Speeds.DISTANCE_PER_PULSE)
    }

    fun setPower(leftPower: Double, rightPower: Double) {
        SmartDashboard.putNumber("leftPower", leftPower)
        SmartDashboard.putNumber("rightPower", rightPower)
        leftA.set(leftPower)
        leftB.set(leftPower)
        rightA.set(rightPower)
        rightB.set(rightPower)
    }

    fun getDistance(): Double {
        val leftEncoderOutput = leftEncoder.getDistance()
        val rightEncoderOutput = rightEncoder.getDistance()
        SmartDashboard.putNumber("leftEncoderOutput", leftEncoderOutput)
        SmartDashboard.putNumber("rightEncoderOutput", rightEncoderOutput)
        return (leftEncoderOutput + rightEncoderOutput) * 0.5
    }
}
