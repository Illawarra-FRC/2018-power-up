package frc.team9761.robot

abstract class Step {
    abstract fun stepInit(robot: Robot): Unit

    // Return true if complete
    abstract fun stepPeriodic(robot: Robot): Boolean
}

class Drive(val distance: Double): Step() {
    var targetDistance: Double = 0.0

    override fun stepInit(robot: Robot): Unit {
        val drivetrain = robot.drivetrain
        val startingDistance = drivetrain.getDistance()
        targetDistance = startingDistance + distance
    }

    override fun stepPeriodic(robot: Robot): Boolean {
        val drivetrain = robot.drivetrain
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
    var targetAngle: Double = 0.0
    override fun stepInit(robot: Robot): Unit {
        val gyro = robot.gyro
        val startingAngle = gyro.getAngle()
        targetAngle = startingAngle - 90
    }

    override fun stepPeriodic(robot: Robot): Boolean {
        val drivetrain = robot.drivetrain
        val gyro = robot.gyro
        var remainingAngle = gyro.getAngle() - targetAngle
        if (remainingAngle < -180)
            remainingAngle += 360
        else if (remainingAngle > 180)
            remainingAngle -= 360

        if (remainingAngle > 0) {
            drivetrain.setPower(-Speeds.AUTO_POWER, Speeds.AUTO_POWER)
            return false
        } else {
            drivetrain.setPower(0.0, 0.0)
            return true
        }    
    }
} 

class TurnRight(): Step() {
    var targetAngle: Double = 0.0
    override fun stepInit(robot: Robot): Unit {
        val gyro = robot.gyro
        val startingAngle = gyro.getAngle()
        targetAngle = startingAngle + 90
    }

    override fun stepPeriodic(robot: Robot): Boolean {
        val drivetrain = robot.drivetrain
        val gyro = robot.gyro
        var remainingAngle = gyro.getAngle() - targetAngle
        if (remainingAngle < -180)
            remainingAngle += 360
        else if (remainingAngle > 180)
            remainingAngle -= 360

        if (remainingAngle < 0) {
            drivetrain.setPower(Speeds.AUTO_POWER, -Speeds.AUTO_POWER)
            return false
        } else {
            drivetrain.setPower(0.0, 0.0)
            return true
        }    
    }
} 

class DepositOnScale(): Step() {
    override fun stepInit(robot: Robot): Unit {}

    override fun stepPeriodic(robot: Robot): Boolean { return true }
}
class DepositOnSwitch(): Step() {
    override fun stepInit(robot: Robot): Unit {}

    override fun stepPeriodic(robot: Robot): Boolean { return true }
}
