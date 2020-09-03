package config.instructions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import config.deserializers.KeyEventDeserializer;
import java.awt.Robot;
import keybinds.KeyManager;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TapKey extends _Instruction {

	@JsonProperty("keyEvent")
	@JsonDeserialize(using = KeyEventDeserializer.class)
	@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
	public int keyEvent = 0;

	@JsonProperty("duration")
	public long duration = 0;

	public TapKey(){
		throw new RuntimeException("give me a goddamm stacktrace");
	}

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
