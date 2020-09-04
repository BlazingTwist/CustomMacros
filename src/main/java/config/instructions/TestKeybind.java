package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.ChangeToActionCallback;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

public class TestKeybind implements Instruction{

	@JsonProperty("keybind")
	private String keybind = null;

	@JsonProperty("actionNameIfPressed")
	private String actionNameIfPressed = null;

	@JsonProperty("actionNameElse")
	private String actionNameElse = null;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		if(configCore.getKeyManager().isModulePressed(keybind)){
			if(actionNameIfPressed == null){
				return new DoneCallback();
			}
			return new ChangeToActionCallback(actionNameIfPressed);
		}else{
			if(actionNameElse == null){
				return new DoneCallback();
			}
			return new ChangeToActionCallback(actionNameElse);
		}
	}
}
