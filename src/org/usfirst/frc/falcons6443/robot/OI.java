package org.usfirst.frc.falcons6443.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import org.usfirst.frc.falcons6443.robot.hardware.joysticks.Xbox;

import java.util.HashMap;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author Christopher Medlin
 */
public class OI {

    private final int PRIMARY_PORT_NUMBER = 0;
    private final int SECONDARY_PORT_NUMBER = 1;

    private Xbox primary;
    private Xbox secondary;

    private HashMap<String, Button> buttons;

    /**
     * Constructor for OI.
     */
    public OI() {
        primary = new Xbox(new XboxController(PRIMARY_PORT_NUMBER));
        secondary = new Xbox(new XboxController(SECONDARY_PORT_NUMBER));
        buttons = new HashMap<String, Button>(4);
    }

    /**
     * Returns the Joystick associated with this OI object.
     *
     * @return the Joystick associated with this OI object.
     */
    public Xbox getXbox(boolean primaryController) { //put your intended joystick here: public Logitech getLogitech(bool...) etc
        if(primaryController) {
            return primary;
        } else {
            return secondary;
        }
    }

    /**
     * Returns the Button object associated with the key.
     *
     * @param key the name of the button.
     * @return the button associated with the key.
     */
    public Button getButton(String key) {
        return buttons.get(key);
    }
}
