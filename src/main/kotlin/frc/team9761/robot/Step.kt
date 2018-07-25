package frc.team9761.robot

abstract class Step {
    abstract fun stepInit(robot: Robot): Unit

    // Return true if complete
    abstract fun stepPeriodic(robot: Robot): Boolean
}

class Drive(val distance: Double): Step() {
    var startTime: Long = 0
    var targetDistance: Double = 0.0

    override fun stepInit(robot: Robot): Unit {
        val drivetrain = robot.drivetrain
        val startingDistance = drivetrain.getDistance()
        startTime = System.currentTimeMillis()
        targetDistance = startingDistance + distance
    }

    override fun stepPeriodic(robot: Robot): Boolean {
        val drivetrain = robot.drivetrain
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - startTime
        val currentDistance = drivetrain.getDistance()
        if (currentDistance < targetDistance && elapsedTime < Speeds.AUTO_DRIVE_MAX_DURATION) {
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
    var startTime: Long = 0

    override fun stepInit(robot: Robot): Unit {
        startTime = System.currentTimeMillis()
    }

    override fun stepPeriodic(robot: Robot): Boolean {
        val lift = robot.lift
        val wrist = robot.wrist
        val intake = robot.intake
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - startTime

        if (elapsedTime < Speeds.LIFT_RAISE_SWITCH_DURATION) {
            if (elapsedTime > Speeds.LIFT_RAISE_RELEASE_DURATION)
                wrist.release()
                
            lift.raise()
            return false
        } else {
            lift.stop()
            if (elapsedTime < Speeds.LIFT_RAISE_SWITCH_DURATION + Speeds.CUBE_EJECT_DURATION) {
                intake.eject()
                return false
            } else {
                intake.stop()
                return true
            }
            
        }
    }
}

class DepositOnSwitch(): Step() {
    var startTime: Long = 0

    override fun stepInit(robot: Robot): Unit {
        startTime = System.currentTimeMillis()
    }

    override fun stepPeriodic(robot: Robot): Boolean {
        val lift = robot.lift
        val wrist = robot.wrist
        val intake = robot.intake
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - startTime

        if (elapsedTime < Speeds.LIFT_RAISE_SCALE_DURATION) {
            if (elapsedTime > Speeds.LIFT_RAISE_RELEASE_DURATION)
                wrist.release()
                
            lift.raise()
            return false
        } else {
            lift.stop()
            if (elapsedTime < Speeds.LIFT_RAISE_SCALE_DURATION + Speeds.CUBE_EJECT_DURATION) {
                intake.eject()
                return false
            } else {
                intake.stop()
                return true
            }
            
        }
    }
}

class WristRelease(): Step() {
    var startTime: Long = 0

    override fun stepInit(robot: Robot): Unit {
        startTime = System.currentTimeMillis()
    }

    override fun stepPeriodic(robot: Robot): Boolean {
        val lift = robot.lift
        val wrist = robot.wrist
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - startTime

        if (elapsedTime < Speeds.LIFT_RAISE_RELEASE_DURATION) {
            lift.raise()
            return false
        } else {
            lift.stop()
            wrist.release()
            return true
        }
    }
}
