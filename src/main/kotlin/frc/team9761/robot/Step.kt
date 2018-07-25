package frc.team9761.robot

abstract class Step {
    abstract fun stepInit(drivetrain: Drivetrain): Unit

    // Return true if complete
    abstract fun stepPeriodic(drivetrain: Drivetrain): Boolean
}

class Drive(val distance: Double): Step() {
    var targetDistance: Double = 0.0

    override fun stepInit(drivetrain: Drivetrain): Unit {
        val startingDistance = drivetrain.getDistance()
        targetDistance = startingDistance + distance
    }

    override fun stepPeriodic(drivetrain: Drivetrain): Boolean {
        val currentDistance = drivetrain.getDistance()
        if (currentDistance < targetDistance) {
            drivetrain.setPower(Speeds.AUTO_POWER, Speeds.AUTO_POWER)
            return false
        } else {
            drivetrain.setPower(0.0, 0.0)
            return true
        }
    }
}

class TurnLeft(): Step() {
    override fun stepInit(drivetrain: Drivetrain): Unit {}

    override fun stepPeriodic(drivetrain: Drivetrain): Boolean { return true }
} 

class TurnRight(): Step() {
    override fun stepInit(drivetrain: Drivetrain): Unit {}

    override fun stepPeriodic(drivetrain: Drivetrain): Boolean { return true }
} 

class DepositOnScale(): Step() {
    override fun stepInit(drivetrain: Drivetrain): Unit {}

    override fun stepPeriodic(drivetrain: Drivetrain): Boolean { return true }
}
class DepositOnSwitch(): Step() {
    override fun stepInit(drivetrain: Drivetrain): Unit {}

    override fun stepPeriodic(drivetrain: Drivetrain): Boolean { return true }
}
