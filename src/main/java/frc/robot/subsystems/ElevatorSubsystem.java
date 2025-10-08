package frc.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Constants.Elevator;

public class ElevatorSubsystem implements Subsystem {
  //
  private static TalonFX motor = new TalonFX(Elevator.MOTOR_ID);
  public Constants.Elevator.height curLevel = Constants.Elevator.height.BOTTOM;

  public ElevatorSubsystem() {}

  private void setHeight(double height) {
    motor.setControl(new MotionMagicVoltage(height));
  }

  public void setHeightLevel(Constants.Elevator.height height) {
    switch (height) {
      case BOTTOM:
        motor.setControl(new MotionMagicVoltage(0.0));
        curLevel = Constants.Elevator.height.BOTTOM;
        break;
      case MIDDLE:
        motor.setControl(new MotionMagicVoltage(0.5));
        curLevel = Constants.Elevator.height.MIDDLE;
        break;
      case TOP:
        motor.setControl(new MotionMagicVoltage(1.0));
        curLevel = Constants.Elevator.height.TOP;
        break;
    }
  }

  public void moveUp() {
    switch (curLevel) {
      case BOTTOM:
        setHeightLevel(curLevel);
        break;
      case MIDDLE:
        setHeightLevel(curLevel);
        break;
      case TOP:
        break;
    }
  }

  public void moveDown() {
    switch (curLevel) {
      case BOTTOM:
        break;
      case MIDDLE:
        curLevel = Constants.Elevator.height.BOTTOM;
        setHeightLevel(curLevel);
        break;
      case TOP:
        curLevel = Constants.Elevator.height.MIDDLE;
        setHeightLevel(curLevel);
        break;
    }
  }
}
