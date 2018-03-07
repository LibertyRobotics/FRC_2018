package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Delay;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;

/**
 * Command to get to leftside scale from second position
 *
 * @author Owen Engbretson
 */
public class RightToLeftScale extends CommandGroup {

    private FlywheelSystem flywheelSystem;

        public RightToLeftScale(){
            addSequential(new DriveToDistance(228));
            addSequential(new RotateToAngle(90));
            addSequential(new DriveToDistance(232));
            addSequential(new RotateToAngle(270));
            addSequential(new DriveToDistance(96));
            addSequential(new RotateToAngle(270));
            addSequential(new DriveToDistance(54));

            //elevator.setToHeight(ElevatorPosition.Scale);
            flywheelSystem.setIntakePosition(IntakePosition.IntakeDownPosition);
            addSequential(new Delay(2));
            flywheelSystem.output();


        }

}
