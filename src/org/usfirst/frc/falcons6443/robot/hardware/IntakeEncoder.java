package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Encoder;
 import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class IntakeEncoder {
     private Encoder encoder;

     public IntakeEncoder(){
         encoder = new Encoder(RobotMap.IntakeEncoderA, RobotMap.IntakeEncoderB);
     }

     public double getDistance(){
         Logger.log(LoggerSystems.Intake,"Intake", Double.toString(encoder.getRaw()));
         return encoder.getRaw();
     }

     public void reset(){
         encoder.reset();
         Logger.log(LoggerSystems.Intake,"Intake", "reset encoder");
     }
 }
