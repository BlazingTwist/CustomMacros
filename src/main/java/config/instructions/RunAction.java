package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Robot;
import java.util.ArrayList;
import keybinds.KeyManager;

public class RunAction implements Instruction {

	@JsonProperty("action")
	ArrayList<Instruction> action = null;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
