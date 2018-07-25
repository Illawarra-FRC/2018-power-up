package frc.team9761.robot

import edu.wpi.first.wpilibj.AnalogInput
import edu.wpi.first.wpilibj.CameraServer
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard

class Robot : IterativeRobot() {
    lateinit var controller: XboxController

    lateinit var drivetrain: Drivetrain
    lateinit var lift: Lift
    lateinit var wrist: Wrist
    lateinit var intake: Intake
    lateinit var startPosition: AnalogInput

    override fun robotInit() {
        println("Hello Illawarra 9761")

        controller = XboxController(Ports.XBOX_CONTROLLER_PORT)
        CameraServer.getInstance().startAutomaticCapture()

        drivetrain = Drivetrain()
        lift = Lift()
        wrist = Wrist()
        intake = Intake()
        startPosition = AnalogInput(Ports.START_POSITION_CHANNEL)
    }

    fun clamp(num: Double): Double {
        if (num < -1.0)
            return -1.0
        if (num > 1.0)
            return 1.0
        return num
    }

    fun optionallyReverse(num: Double, switch: Boolean): Double {
        if (switch)
            return -num

        return num
    }

    fun identifyStartPosition(startPositionVoltage: Double): Int {
        if (startPositionVoltage < 5.0/3.0)
            return -1 // left

        if (startPositionVoltage > 5.0*2.0/3.0)
            return 1 // right

        return 0 // centre
    }

    var startTime: Long = 0
    lateinit var strategy: Strategy

    override fun autonomousInit() {
        startTime = System.currentTimeMillis()
        val gameData = DriverStation.getInstance().gameSpecificMessage
        SmartDashboard.putString("gameData", gameData)
        val startPositionVoltage = startPosition.getVoltage()
        SmartDashboard.putNumber("startPositionVoltage", startPositionVoltage)
        val identifiedStartPosition = identifyStartPosition(startPositionVoltage)
        SmartDashboard.putNumber("identifiedStartPosition", identifiedStartPosition.toDouble())

        if (identifiedStartPosition == -1) {
            if (gameData[0] == 'L')
                strategy = LeftToLeftSwitch
            else if (gameData[1] == 'L')
                strategy = LeftToLeftScale
            else
                strategy = LeftToCrossLine
        } else if (identifiedStartPosition == 0) {
            if (gameData[0] == 'L')
                strategy = CentreToLeftSwitch
            else
                strategy = CentreToRightSwitch
        } else {
            if (gameData[0] == 'R')
                strategy = RightToRightSwitch
            else if (gameData[1] == 'R')
                strategy = RightToRightScale
            else
                strategy = RightToCrossLine
        }
    }

    override fun autonomousPeriodic() {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - startTime

        var drivePower = if (elapsedTime < Speeds.CROSS_LINE_DURATION) Speeds.CROSS_LINE_POWER else 0.0
        drivetrain.setPower(drivePower, drivePower)

        if (elapsedTime < Speeds.LIFT_RAISE_DURATION)
            lift.raise()
        else {
            lift.stop()
            wrist.release()
        }
    }

    override fun teleopPeriodic() {
        run {
            val x = controller.getY(Hand.kLeft)
            val y = controller.getX(Hand.kRight)
            SmartDashboard.putNumber("x", x)
            SmartDashboard.putNumber("y", y)

            // based on getBButton(), reverse x
            val orx = optionallyReverse(x, controller.getBButton())
            SmartDashboard.putNumber("orx", orx)

            var leftPower = clamp(y + orx)
            var rightPower = clamp(y - orx)
            drivetrain.setPower(leftPower, rightPower)
        }

        run {
            val leftBumper = controller.getBumper(Hand.kLeft)
            val rightBumper = controller.getBumper(Hand.kRight)
            if (leftBumper)
                lift.raise()
            else if (rightBumper)
                lift.lower()
            else
                lift.stop()
        }

        run {
            val yButton = controller.getYButton()
            val aButton = controller.getAButton()
            if (yButton)
                wrist.raise()
            else if (aButton)
                wrist.lower()
            else
                wrist.stop()
        }

        run {
            val leftTrigger: Boolean = (controller.getTriggerAxis(Hand.kLeft) > Speeds.TRIGGER_THRESHOLD)
            val rightTrigger: Boolean = (controller.getTriggerAxis(Hand.kRight) > Speeds.TRIGGER_THRESHOLD)
            if (rightTrigger)
                intake.grab()
            else if (leftTrigger)
                intake.eject()
            else
                intake.stop()
        }
    }
}
