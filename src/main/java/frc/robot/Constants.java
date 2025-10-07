// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import frc.robot.generated.TunerConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static final). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class Driver {
    public static final int CONTROLLER_PORT = 0;

    public static final double MAX_SPEED = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
    public static final double LOW_MAX_SPEED = MAX_SPEED / 2;

    public static final double MAX_ANGULAR_SPEED = RotationsPerSecond.of(1.5).in(RadiansPerSecond);
    public static final double LOW_MAX_ANGULAR_SPEED = MAX_ANGULAR_SPEED / 2;
  }

  public static class Elevator {
    public static final int MOTOR_ID = 18;
    public static final double CURRENT_LIMIT = 80.0;

    public static final TalonFXConfiguration elevatorConfigs = new TalonFXConfiguration();

    static {
      elevatorConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
      elevatorConfigs.CurrentLimits.StatorCurrentLimit = CURRENT_LIMIT;
      elevatorConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      elevatorConfigs.MotorOutput.Inverted =
          InvertedValue.Clockwise_Positive; // positive should make it go up
      // Assuming CANcoder is after gear reduction, and encoder is zeroed at horizontal
      elevatorConfigs.Slot0.GravityType = GravityTypeValue.Elevator_Static;

      elevatorConfigs.Slot0.kG = -0.3; // NOTE: a spring exists on the elevator, holding it up
      elevatorConfigs.Slot0.kS = 0.0;
      elevatorConfigs.Slot0.kV = 15.0;
      elevatorConfigs.Slot0.kA = 0.0;
      elevatorConfigs.Slot0.kP = 40.0;
      elevatorConfigs.Slot0.kI = 0.0;
      elevatorConfigs.Slot0.kD = 2.0;
      elevatorConfigs.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
      // https://www.chiefdelphi.com/t/motion-magic-help-ctre/483319/2
      elevatorConfigs.MotionMagic.MotionMagicCruiseVelocity = 2.0; // Target cruise velocity in rps
      elevatorConfigs.MotionMagic.MotionMagicAcceleration = 4.0; // Target acceleration in rps/s
      elevatorConfigs.MotionMagic.MotionMagicJerk = 40.0; // Target jerk in rps/(s^2)
      elevatorConfigs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
      elevatorConfigs.Feedback.SensorToMechanismRatio = 135.0;
      elevatorConfigs.Feedback.RotorToSensorRatio = 1.0; // note: does nothing
    }
  }

  public static class Shooter {
    public static final int RIGHT_SHOOTER_ID = 12;
    public static final int LEFT_SHOOTER_ID = 14;
    public static final double SHOOTER_CURRENT_LIMIT = 80.0;

    public static final int RIGHT_FEED_ID = 13;
    public static final int LEFT_FEED_ID = 15;
    public static final int FEED_CURRENT_LIMIT = 40;

    public static final int ARM_ID = 16;
    public static final int ARM_ENCODER_ID = 57;
    public static final double ARM_CURRENT_LIMIT = 80.0;

    public static final TalonFXConfiguration shooterConfigs = new TalonFXConfiguration();

    static {
      shooterConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
      shooterConfigs.CurrentLimits.StatorCurrentLimit = SHOOTER_CURRENT_LIMIT;
      shooterConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      shooterConfigs.MotorOutput.Inverted =
          InvertedValue.CounterClockwise_Positive; // right is primary, positive is out

      shooterConfigs.Slot0.kS = 0.0; // Add kS V output to overcome static friction
      shooterConfigs.Slot0.kV = 0.0; // A velocity target of 1 rps results in kV V output
      shooterConfigs.Slot0.kA = 0.0; // An acceleration of 1 rps/s requires kA V output
      shooterConfigs.Slot0.kP = 0.0; // An error of 1 rps results in kP V output
      shooterConfigs.Slot0.kI = 0.0; // integrated error (0: no output for error)
      shooterConfigs.Slot0.kD = 0.0; // error derivative
      shooterConfigs.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
    }

    public static final TalonFXConfiguration armConfigs = new TalonFXConfiguration();

    static {
      armConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
      armConfigs.CurrentLimits.StatorCurrentLimit = ARM_CURRENT_LIMIT;
      armConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      armConfigs.MotorOutput.Inverted =
          InvertedValue.CounterClockwise_Positive; // positive should make it go up
      // Assuming CANcoder is after gear reduction, and encoder is zeroed at horizontal

      armConfigs.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
      armConfigs.Slot0.kG = 0.1;
      armConfigs.Slot0.kS = 0.0;
      armConfigs.Slot0.kV = 0.0;
      armConfigs.Slot0.kA = 0.0;
      armConfigs.Slot0.kP = 30.0;
      armConfigs.Slot0.kI = 0.0;
      armConfigs.Slot0.kD = 1.0;
      armConfigs.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
      // https://www.chiefdelphi.com/t/motion-magic-help-ctre/483319/2
      armConfigs.MotionMagic.MotionMagicCruiseVelocity = 0.2; // Target cruise velocity in rps
      armConfigs.MotionMagic.MotionMagicAcceleration = 0.4; // Target acceleration in rps/s
      armConfigs.MotionMagic.MotionMagicJerk = 0.8; // Target jerk in rps/(s^2)
      armConfigs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;
      armConfigs.Feedback.FeedbackRemoteSensorID = ARM_ENCODER_ID;
      armConfigs.Feedback.SensorToMechanismRatio = 1.0;
    }
  }
}
