package frc.team9761.robot

object Ports {
    
    // SNES Controller Buttons
    val SNES_X_BUTTON = 1
    val SNES_A_BUTTON = 2
    val SNES_B_BUTTON = 3
    val SNES_Y_BUTTON = 4
    val SNES_LEFT_TRIGGER = 5
    val SNES_RIGHT_TRIGGER = 6

    val XBOX_CONTROLLER_PORT = 0
    val SNES_CONTROLLER_PORT = 1

    // CAN Bus Node IDs
    val LIFT_LEFT_A_CANID = 1
    val LIFT_LEFT_B_CANID = 2
    val LIFT_RIGHT_A_CANID = 3
    val LIFT_RIGHT_B_CANID = 4

    // PWM control channels
    val DRIVE_RIGHT_A_CHANNEL = 1
    val DRIVE_RIGHT_B_CHANNEL = 2
    val DRIVE_LEFT_A_CHANNEL = 3
    val DRIVE_LEFT_B_CHANNEL = 4
    val WRIST_MOTOR_CHANNEL = 5
    val INTAKE_LEFT_CHANNEL = 6
    val INTAKE_RIGHT_CHANNEL = 7
    val WRIST_SERVO_CHANNEL = 8

    // Analog input channel
    val START_POSITION_CHANNEL = 0

    // DIO ports
    val ENCODER_LEFT_A_CHANNEL = 0
    val ENCODER_LEFT_B_CHANNEL = 1
    val ENCODER_RIGHT_A_CHANNEL = 2
    val ENCODER_RIGHT_B_CHANNEL = 3
    
    // Pneumatic ports
    val SOLENOID_FORWARD_CHANNEL = 0
    val SOLENOID_REVERSE_CHANNEL = 1
}
