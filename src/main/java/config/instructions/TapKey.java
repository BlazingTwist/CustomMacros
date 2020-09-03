package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import config.deserializers.KeyEventDeserializer;
import java.awt.Robot;
import keybinds.KeyManager;

public class TapKey implements Instruction {

	@JsonProperty("keyEvent")
	@JsonDeserialize(using = KeyEventDeserializer.class)
	@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
	public int keyEvent = 0;

	@JsonProperty("duration")
	public long duration = 0;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
