package config.instructions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import config.pixelMatching.PixelStateCollection;
import java.awt.Robot;
import keybinds.KeyManager;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = JsonNodeDeserializer.class)
public class WaitForPixelState extends _Instruction {

	@JsonProperty("pixelStateCollection")
	PixelStateCollection pixelStateCollection = null;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
