package config.instructions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import java.awt.Robot;
import java.util.ArrayList;
import keybinds.KeyManager;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = JsonNodeDeserializer.class)
public class RunAction extends _Instruction {

	@JsonProperty("action")
	ArrayList<_Instruction> action = null;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
