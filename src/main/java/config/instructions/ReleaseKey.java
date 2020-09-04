package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.deserializers.KeyEventDeserializer;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

public class ReleaseKey implements Instruction {

	@JsonProperty("keyEvent")
	@JsonDeserialize(using = KeyEventDeserializer.class)
	@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
	public int keyEvent = 0;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		robot.keyRelease(keyEvent);
		return new DoneCallback();
	}
}
