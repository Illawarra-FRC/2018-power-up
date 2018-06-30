package frc.team6434.robot


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.Talon

class Intake {
 

      val intakeLeft = Talon(Ports.INTAKE_LEFT_CHANNEL)
      val intakeRight = Talon(Ports.INTAKE_RIGHT_CHANNEL)
      

      fun grab() {
        setIntakePower(Speeds.IN_POWER)
      }

      fun eject() {
        setIntakePower(Speeds.OUT_POWER)
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