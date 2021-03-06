package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.deserializers.KeyEventDeserializer;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.EncounteredExceptionCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

public class TapKey implements Instruction {

	@JsonProperty("keyEvent")
	@JsonDeserialize(using = KeyEventDeserializer.class)
	@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
	public int keyEvent = 0;

	@JsonProperty("duration")
	public long duration = 0;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		robot.keyPress(keyEvent);
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			return new EncounteredExceptionCallback(e);
		} finally {
			robot.keyRelease(keyEvent);
		}
		return new DoneCallback();
	}
}
