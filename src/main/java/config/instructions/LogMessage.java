package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

public class LogMessage implements Instruction{

	@JsonProperty("message")
	private String message = null;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		System.out.println(message);
		return new DoneCallback();
	}
}
