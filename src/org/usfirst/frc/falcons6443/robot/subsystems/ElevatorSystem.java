package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
//import org.usfirst.frc.falcons6443.robot.hardware.Encoders;
import org.usfirst.frc.falcons6443.robot.hardware.ElevatorMotor;
import org.usfirst.frc.falcons6443.robot.hardware.LimitSwitch;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

public class ElevatorSystem extends Subsystem {

    public ElevatorMotor motor;
    public LimitSwitch scaleLimit;
    public LimitSwitch bottomLimit;
    //private Encoder encoder;
    public Timer timer;

    public ElevatorPosition desiredState = ElevatorPosition.Exchange;
    public ElevatorPosition previousLimit = ElevatorPosition.UnderSwitch;

    public final double upSpeed = 0.9;
    public final double downSpeed = -0.6;
    public final double constantSpeed = 0.15;
    public final double switchHeight = 600000; //setSpeed in ticks//630000
    public final double scaleHeight = 1230000; //setSpeed in ticks

    public final double autoTimeOneMotor = 5;
    public final double autoTimeRedlines = 1;
    public double autoTime;
    public boolean isManual;

    public ElevatorSystem(){
        if(RobotMap.RedLine){
            //encoder = new Encoder(RobotMap.ElevatorEncoderA, RobotMap.ElevatorEncoderB, false, Encoder.EncodingType.k4X);
            autoTime = autoTimeRedlines;
        } else {
            autoTime = autoTimeOneMotor;
        }
        motor = new ElevatorMotor();
        scaleLimit = new LimitSwitch(RobotMap.ElevatorScaleLimit);
        bottomLimit = new LimitSwitch(RobotMap.ElevatorBottomLimit);
        timer = new Timer();
        isManual = false;
    }

    @Override
    public void initDefaultCommand() { }

    public void startTimer(){ timer.start(); }
    public void stopTimer(){ timer.stop(); }
    public double getTime(){ return timer.get(); }

    public double getEncoderDistance(){
        //return encoder.getDistance();
        return -1;
        }
    public double getEncoderDistanceAuto(){
        //return -encoder.getDistance();
        return -1;
    }

    public void resetEncoder() { /*encoder.reset(); */}

    public void updatePreviousLimit(){
      /*  if (scaleLimit.get() || encoder.getDistance() > switchHeight){
            previousLimit = ElevatorPosition.OverSwitch;
        } else if(bottomLimit.get() || encoder.getDistance() < switchHeight){
            previousLimit = ElevatorPosition.UnderSwitch;
        }*/
    }

    public void setToHeight (ElevatorPosition elevatorState){
//        Logger.log(LoggerSystems.Elevator,"Set elevator position: " + elevatorState.getName());
        switch (elevatorState){
            case Exchange:
                desiredState = ElevatorPosition.Exchange;
                setManual(false);
                break;
            case Switch:
                desiredState = ElevatorPosition.Switch;
                setManual(false);
                break;
            case Scale:
                desiredState = ElevatorPosition.Scale;
                setManual(false);
                break;
            case Stop:
                desiredState = ElevatorPosition.Stop;
                break;
        }
    }

    public boolean getSwitchHeight(){
        return getEncoderDistanceAuto() > switchHeight;
    }
    public boolean getBottomHeight(){
        return bottomLimit.get();
    }
    public boolean getScaleHeight(){
        if(scaleLimit.get()){
            return true;
        } else if (/*encoder.getDistance() > scaleHeight*/ false){
            return true;
        } else if (/*encoder.getDistance() < scaleHeight*/ true){
            return false;
        }
        return scaleLimit.get();
    }

    public void stop () {
        motor.setSpeed(constantSpeed);
    }

    public void manual(double x){
        setManual(true);
        if(Math.abs(x) < constantSpeed) x = constantSpeed;
        if(x < 0) x = x * .4;

       /* if(!bottomLimit.get() && x < 0) {
            motor.setSpeed(0);
            resetEncoder();
        } else if(encoder.getDistance() < scaleHeight && scaleLimit.get()){
            motor.setSpeed(x);
        } else {
            if(x < 0) motor.setSpeed(x);
            else motor.setSpeed(constantSpeed);
            System.out.println("Max Height!!");
        }*/
       motor.setSpeed(x);
        //System.out.println("E Enc: " + encoder.get());
        System.out.println("Manual");System.out.println("Manual");
    }

    public void setManual(boolean on){
        isManual = on;
        Logger.log(LoggerSystems.Elevator, "Set manual " + on);
    }

    public boolean getManual(){ return isManual; }
}