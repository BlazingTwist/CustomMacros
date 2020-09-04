package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.EncounteredExceptionCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;
import java.io.IOException;

public class ExecCommand implements Instruction {

	@JsonProperty("command")
	String command = null;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		Runtime runtime = Runtime.getRuntime();
		try{
			runtime.exec(command);
		}catch(IOException e){
			return new EncounteredExceptionCallback(
					new RuntimeException("unable to execute command: '" + command + "'!", e));
		}
		return new DoneCallback();
	}
}
