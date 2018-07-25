package frc.team9761.robot

abstract class Step {
}

class Drive(distance: Double): Step() 

class TurnLeft(): Step() 
class TurnRight(): Step() 

class DepositOnScale(): Step() 
class DepositOnSwitch(): Step() 
