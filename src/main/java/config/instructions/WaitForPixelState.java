package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.pixelMatching.PixelStateCollection;
import java.awt.Robot;
import keybinds.KeyManager;

public class WaitForPixelState implements Instruction {

	@JsonProperty("pixelStateCollection")
	PixelStateCollection pixelStateCollection = null;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
