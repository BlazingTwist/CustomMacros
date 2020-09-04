package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.ChangeToActionCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

public class ChangeToAction implements Instruction {

	@JsonProperty("actionName")
	public String actionName = null;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		return new ChangeToActionCallback(actionName);
	}
}
