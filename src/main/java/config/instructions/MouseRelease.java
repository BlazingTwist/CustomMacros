package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.EncounteredExceptionCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseRelease implements Instruction {

	@JsonProperty("mouseButton")
	private int mouseButton = 0;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		if (mouseButton > MouseInfo.getNumberOfButtons()) {
			return new EncounteredExceptionCallback(
					new IllegalArgumentException("invalid mouseButton: " + mouseButton
							+ " | only " + MouseInfo.getNumberOfButtons() + " buttons are pressable!"));
		}
		robot.mouseRelease(InputEvent.getMaskForButton(mouseButton));
		return new DoneCallback();
	}
}
