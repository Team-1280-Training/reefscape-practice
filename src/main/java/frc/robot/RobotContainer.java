// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.Driver;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private boolean loweredSpeed = true;

  // The robot's subsystems and commands are defined here
  private final Field2d fieldMap = new Field2d();
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

  private final Telemetry telemetry = new Telemetry(Driver.MAX_SPEED);

  private final CommandXboxController driverController =
      new CommandXboxController(Driver.CONTROLLER_PORT);

  /** The container for the robot. Contains subsystems, IO devices, and commands. */
  public RobotContainer() {
    configureBindings();
  }

  public void updateDashboard() {
    SmartDashboard.putData("Field Map", fieldMap);
  }

  public void configureBindings() {
    drivetrain.registerTelemetry(telemetry::telemeterize);

    { // Driver controls
      int moveTaps = 10;
      int rotationTaps = 10;
      LinearFilter filterX = LinearFilter.movingAverage(moveTaps);
      LinearFilter filterY = LinearFilter.movingAverage(moveTaps);
      LinearFilter filterRotation = LinearFilter.movingAverage(rotationTaps);

      /* Setting up bindings for necessary control of the swerve drive platform */
      final SwerveRequest.FieldCentric driveRequest =
          new SwerveRequest.FieldCentric()
              .withDeadband(Driver.MAX_SPEED * 0.02)
              .withRotationalDeadband(Driver.MAX_ANGULAR_SPEED * 0.02) // Add a 10% deadband
              .withDriveRequestType(
                  DriveRequestType.Velocity); // Use open-loop control for drive motors
      drivetrain.setDefaultCommand(
          drivetrain.applyRequest(
              () -> {
                double speed = loweredSpeed ? Driver.LOW_MAX_SPEED : Driver.MAX_SPEED;
                double angularSpeed =
                    loweredSpeed ? Driver.LOW_MAX_ANGULAR_SPEED : Driver.MAX_ANGULAR_SPEED;
                return driveRequest
                    .withVelocityX(
                        filterX.calculate(
                            MathUtil.applyDeadband(driverController.getLeftY(), 0.1) * speed))
                    .withVelocityY(
                        filterY.calculate(
                            MathUtil.applyDeadband(driverController.getLeftX(), 0.1) * speed))
                    .withRotationalRate(
                        filterRotation.calculate(
                            -MathUtil.applyDeadband(driverController.getRightX(), 0.1)
                                * angularSpeed));
              }));

      driverController
          .leftStick()
          .onTrue(drivetrain.runOnce(() -> drivetrain.resetRotation(Rotation2d.kZero)));
    }

    { // Operator controls
    }
  }

  public void updateDrivetrainPose() {
    fieldMap.setRobotPose(drivetrain.getState().Pose);
  }
}
