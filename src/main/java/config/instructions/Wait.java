package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.EncounteredExceptionCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

public class Wait implements Instruction {

	@JsonProperty("duration")
	public long duration = 0;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		try{
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			return new EncounteredExceptionCallback(e);
		}
		return new DoneCallback();
	}
}
