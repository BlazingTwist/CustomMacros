package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.pixelMatching.PixelStateCollection;
import java.awt.Robot;
import keybinds.KeyManager;

public class ProbeScreen implements Instruction {

	@JsonProperty("pixelStateCollection")
	public PixelStateCollection pixelStateCollection = null;

	@JsonProperty("actionNameIfMatch")
	public String actionNameIfMatch = null;

	@JsonProperty("actionNameElse")
	public String actionNameElse = null;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
