package frc.team9761.robot

abstract class Strategy {
    abstract fun name(): String

    abstract fun steps(): Array<Step>
}


object LeftToLeftSwitch: Strategy() {
    override fun name(): String = "LeftToLeftSwitch"

    override fun steps(): Array<Step> = arrayOf(
        Drive(3.3),
        TurnRight(),
        Drive(1.0),
        DepositOnSwitch()
    )
}



object LeftToLeftScale: Strategy() {
    override fun name(): String = "LeftToLeftScale"

    override fun steps(): Array<Step> = arrayOf(
        Drive(8.3),
        TurnRight(),
        Drive(0.2),
        DepositOnScale()
    )
}


object LeftToCrossLine: Strategy() {
    override fun name(): String = "LeftToCrossLine"

    override fun steps(): Array<Step> = arrayOf(
        Drive(3.0), // ideally 6
        TurnRight(),
        WristRelease()
    )
}


object CentreToLeftSwitch: Strategy() {
    override fun name(): String = "CentreToLeftSwitch"

    override fun steps(): Array<Step> = arrayOf(
        Drive(1.0),
        TurnLeft(),
        Drive(2.0),
        TurnRight(),
        Drive(2.0),
        DepositOnSwitch()
    )
}


object CentreToRightSwitch: Strategy() {
    override fun name(): String = "CentreToRightSwitch"

    override fun steps(): Array<Step> = arrayOf(
        Drive(1.0),
        TurnRight(),
        Drive(1.0),
        TurnLeft(),
        Drive(2.0),
        DepositOnSwitch()
    )
}


object RightToRightSwitch: Strategy() {
    override fun name(): String = "RightToRightSwitch"

    override fun steps(): Array<Step> = arrayOf(
        Drive(3.3),
        TurnLeft(),
        Drive(1.0),
        DepositOnSwitch()
    )
}


object RightToRightScale: Strategy() {
    override fun name(): String = "RightToRightScale"

    override fun steps(): Array<Step> = arrayOf(
        Drive(8.3),
        TurnLeft(),
        Drive(0.2),
        DepositOnScale()
    )
}


object RightToCrossLine: Strategy() {
    override fun name(): String = "RightToCrossLine"

    override fun steps(): Array<Step> = arrayOf(
        Drive(6.0),
        TurnLeft(),
        WristRelease()
    )
}

