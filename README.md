# Intermediate Programming Training
welcome

Start at [`src/main/java/frc/robot/subsystems/ElevatorSubsystem.java`](src/main/java/frc/robot/subsystems/ElevatorSubsystem.java)

## Instructors Instructions

### Plan

Small tour of `RobotContainer.java`

Elevator:
- Creating and configuring TalonFX motor + id
    - Check current limit
- Constants file
- Read documentation websites (ctre)
- Setpoint and motor requests; show caching later
- RobotContainer:
    - Create elevator subsystem field
- Test:
    - Set height using method, directly
    - Driverstation
    - Deploy command
    - Enabling and disabling, and spacebar
- State enums and height constants; 5 states, 25% increments
- RobotContainer:
    - New controller
    - Controller input and callbacwwwks
- Test, state cycling
- Dashboard
- Test, values and setHeight setter

Shooter:
- Arm:
    - Create arm motor; pre-filled PID (too difficult)
    - Creating and calibrating the CANcoder, phoenix tuner (reset config for each trainee)
    - Arm angles and states - 5 positions, 45 degree increments
    - Controller input for arm
    - Test, arm
- Shooting:
    - TalonFX leader + follower shoot motors
    - New TalonFX request type (MotionMagicVelocityVoltage)
    - Creating SparkMax motors and creating SparkMaxConfig
        - Install REVLib vendor dependency
    - SparkMax set speed or voltage
    - Command groups and command types - shoot procedures and controls
    - Shoot motor PID and config
    - Test and tune with phoenix tuner and ball
