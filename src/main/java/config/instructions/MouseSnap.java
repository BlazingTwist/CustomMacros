package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

public class MouseSnap implements Instruction {

	@JsonProperty("x")
	private int x;

	@JsonProperty("y")
	private int y;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		robot.mouseMove(displayConfig.applyXScale(x), displayConfig.applyYScale(y));
		return new DoneCallback();
	}
}
