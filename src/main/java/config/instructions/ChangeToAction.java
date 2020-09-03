package config.instructions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import java.awt.Robot;
import keybinds.KeyManager;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = JsonNodeDeserializer.class)
public class ChangeToAction extends _Instruction {

	@JsonProperty("actionName")
	public String actionName = null;

	@Override
	public void run(Robot robot, KeyManager keyManager) {
		// TODO
	}
}
