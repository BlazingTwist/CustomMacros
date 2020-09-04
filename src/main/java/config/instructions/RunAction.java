package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.InsertActionCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;
import java.util.ArrayList;

public class RunAction implements Instruction {

	@JsonProperty("action")
	ArrayList<Instruction> action = null;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		return new InsertActionCallback(action);
	}
}
