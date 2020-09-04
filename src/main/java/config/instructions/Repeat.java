package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.InsertActionCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;
import java.util.ArrayList;

public class Repeat implements Instruction {

	@JsonProperty("count")
	public int count = 0;

	@JsonProperty("action")
	public ArrayList<Instruction> action = null;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		ArrayList<Instruction> unwrappedInstructions = new ArrayList<>();
		for(int i = 0; i < count; i++){
			unwrappedInstructions.addAll(action);
		}
		return new InsertActionCallback(unwrappedInstructions);
	}
}
