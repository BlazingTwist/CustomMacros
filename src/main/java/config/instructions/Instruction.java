package config.instructions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.awt.Robot;
import keybinds.KeyManager;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = ChangeToAction.class, name = "changeToAction"),
		@JsonSubTypes.Type(value = ProbeScreen.class, name = "probeScreen"),
		@JsonSubTypes.Type(value = Repeat.class, name = "repeat"),
		@JsonSubTypes.Type(value = RunAction.class, name = "runAction"),
		@JsonSubTypes.Type(value = TapKey.class, name = "tapKey"),
		@JsonSubTypes.Type(value = Wait.class, name = "wait"),
		@JsonSubTypes.Type(value = WaitForPixelState.class, name = "waitForPixelState")
})
public interface Instruction {
	void run(Robot robot, KeyManager keyManager);
}
