package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Robot;
import java.util.ArrayList;
import keybinds.KeyManager;

public class Repeat implements Instruction {

	@JsonProperty("count")
	public int count = 0;

	@JsonProperty("action")
	public ArrayList<Instruction> action = null;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
