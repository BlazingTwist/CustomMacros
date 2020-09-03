package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Robot;
import keybinds.KeyManager;

public class ChangeToAction implements Instruction {

	@JsonProperty("actionName")
	public String actionName = null;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
