package frc.team9761.robot

import com.analog.adis16448.frc.ADIS16448_IMU
import edu.wpi.first.wpilibj.AnalogInput
import edu.wpi.first.wpilibj.CameraServer
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.interfaces.Gyro
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.Joystick

class Robot : IterativeRobot() {
    lateinit var controller: XboxController
    lateinit var snes: Joystick

    lateinit var drivetrain: Drivetrain
    lateinit var lift: Lift
    lateinit var wrist: Wrist
    lateinit var intake: Intake
    lateinit var startPosition: AnalogInput
    lateinit var gyro: Gyro

    override fun robotInit() {
        println("Hello Illawarra 9761")

        controller = XboxController(Ports.XBOX_CONTROLLER_PORT)
        snes = Joystick(Ports.SNES_CONTROLLER_PORT)
        CameraServer.getInstance().startAutomaticCapture()

        drivetrain = Drivetrain()
        drivetrain.init()

        lift = Lift()
        wrist = Wrist()
        intake = Intake()
        startPosition = AnalogInput(Ports.START_POSITION_CHANNEL)

        gyro = ADIS16448_IMU()
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

    lateinit var strategy: Strategy
    var stepIndex: Int = 0

    override fun autonomousInit() {
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
        SmartDashboard.putString("Strategy", strategy.name())
        val steps = strategy.steps()
        stepIndex = 0
        SmartDashboard.putNumber("stepIndex", stepIndex.toDouble())
        steps[stepIndex].stepInit(this)
    }

    override fun autonomousPeriodic() {
        val steps = strategy.steps()
        if (stepIndex < steps.size) {
            if (steps[stepIndex].stepPeriodic(this)) {
                stepIndex = stepIndex + 1
                SmartDashboard.putNumber("stepIndex", stepIndex.toDouble())
                steps[stepIndex].stepInit(this)
            }
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
            val liftInput = snes.getY(Hand.kLeft)
            SmartDashboard.putNumber("liftInput", liftInput)
            if (liftInput < -0.2)
                lift.lower()
            else if (liftInput > 0.2)
                lift.raise()
            else
                lift.stop()


/*
            val leftBumper = controller.getBumper(Hand.kLeft)
            val rightBumper = controller.getBumper(Hand.kRight)
            if (leftBumper)
                lift.lower()
            else if (rightBumper)
                lift.raise()
            else
                lift.stop()
*/                
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
            val snesTrigger: Boolean = snes.getButton(Joystick.ButtonType.kTrigger)
            val snesTop: Boolean = snes.getButton(Joystick.ButtonType.kTop)
            if (snesTrigger)
                intake.grab()
            else if (snesTop)
                intake.eject()
            else
                intake.stop()

/*
            val leftTrigger: Boolean = (controller.getTriggerAxis(Hand.kLeft) > Speeds.TRIGGER_THRESHOLD)
            val rightTrigger: Boolean = (controller.getTriggerAxis(Hand.kRight) > Speeds.TRIGGER_THRESHOLD)
            if (rightTrigger)
                intake.grab()
            else if (leftTrigger)
                intake.eject()
            else
                intake.stop()
*/                
        }
    }
}
