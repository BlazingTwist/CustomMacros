package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Robot;
import keybinds.KeyManager;

public class Wait implements Instruction {

	@JsonProperty("duration")
	public long duration = 0;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
