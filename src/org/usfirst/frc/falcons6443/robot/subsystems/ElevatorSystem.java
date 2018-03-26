package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class ElevatorSystem extends Subsystem {

    private Spark motor;

    private DigitalInput scaleLimit;
    private DigitalInput switchLimit;
    private DigitalInput bottomLimit;

    private ElevatorPosition desiredState = ElevatorPosition.Exchange;
    private ElevatorPosition previousLimit = ElevatorPosition.UnderSwitch;

    public boolean manual = false;

    public ElevatorSystem(){
        motor = new Spark (RobotMap.ElevatorMotor);
        scaleLimit = new DigitalInput (RobotMap.ElevatorScaleLimit);
        switchLimit = new DigitalInput (RobotMap.ElevatorSwitchLimit);
        bottomLimit = new DigitalInput (RobotMap.ElevatorBottomLimit);
        motor.setInverted(true);
    }

    @Override
    public void initDefaultCommand() {
    }

    private void updatePreviousLimit(){
        if (!scaleLimit.get()){
            previousLimit = ElevatorPosition.OverSwitch;
        } else if(!bottomLimit.get()){
            previousLimit = ElevatorPosition.UnderSwitch;
        }
    }

    public void setToHeight (ElevatorPosition elevatorState){
        Logger.log(LoggerSystems.Elevator,"Set elevator position", elevatorState.getValue());
        manual = false;
        switch (elevatorState){
            case Exchange:
                desiredState = ElevatorPosition.Exchange;
                break;
            case Switch:
                desiredState = ElevatorPosition.Switch;
                break;
            case Scale:
                desiredState = ElevatorPosition.Scale;
                break;
            case Stop:
                desiredState = ElevatorPosition.Stop;
                break;
        }
    }

    //put in periodic function
    public void moveToHeight(){
        double power = 0;
        updatePreviousLimit();

        switch (desiredState) {
            case Exchange:
                if (!bottomLimit.get()){
                    Logger.log(LoggerSystems.Elevator,"Bottom limit", Boolean.toString(!bottomLimit.get()));
                    power = 0;
                } else {
                    Logger.log(LoggerSystems.Elevator,"Bottom limit", Boolean.toString(!bottomLimit.get()));
                    power = -1;
                }
                break;
            case Scale:
                if (!scaleLimit.get()){
                    Logger.log(LoggerSystems.Elevator,"Scale limit", Boolean.toString(!scaleLimit.get()));
                    power = 0;
                } else {
                    Logger.log(LoggerSystems.Elevator,"Scale limit", Boolean.toString(!scaleLimit.get()));
                    power = 1;
               }
                break;
            case Switch:
                if (!switchLimit.get()){
                    Logger.log(LoggerSystems.Elevator,"Switch limit", Boolean.toString(!switchLimit.get()));
                    power = 0;
                } else {
                    Logger.log(LoggerSystems.Elevator,"Switch limit", Boolean.toString(!switchLimit.get()));
                    if (previousLimit == ElevatorPosition.UnderSwitch){
                        power = 1;
                    } else {
                        power = -1;
                    }
                }
                break;
            case Stop:
                Logger.log(LoggerSystems.Elevator,"move to height", "Stop enum");
                power = 0;
                break;
        }
        if (!manual) { motor.set(power);} else {
            Logger.log(LoggerSystems.Elevator,"manual", "on");
        }
    }

    public void up (boolean on){
        if (on){
            motor.set(1);
            manual = true;
        }
    }

    public void down (boolean on){
        if (on){
            motor.set(-1);
            manual = true;
        }
    }

    public void stop () {
        motor.set(0);
    }
}
