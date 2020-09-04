package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.EncounteredExceptionCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

public class WaitForKeybind implements Instruction {

	@JsonProperty("keybind")
	private String keybind = null;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		configCore.getKeyManager().addKeybindCallback(this::keybindCallback);
		synchronized (this){
			try {
				wait();
			} catch (InterruptedException e) {
				return new EncounteredExceptionCallback(new RuntimeException("WaitForKeybind got interrupted!", e));
			}
		}
		configCore.getKeyManager().removeKeybindCallback(this::keybindCallback);
		return new DoneCallback();
	}

	private void keybindCallback(String keybind){
		if(keybind.equalsIgnoreCase(this.keybind)){
			synchronized (this){
				this.notify();
			}
		}
	}
}
