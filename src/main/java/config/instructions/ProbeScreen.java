package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.ChangeToActionCallback;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.InstructionCallback;
import config.pixelMatching.PixelStateCollection;
import java.awt.Robot;

public class ProbeScreen implements Instruction {

	@JsonProperty("pixelStateCollection")
	public PixelStateCollection pixelStateCollection = null;

	@JsonProperty("actionNameIfMatch")
	public String actionNameIfMatch = null;

	@JsonProperty("actionNameElse")
	public String actionNameElse = null;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		if(pixelStateCollection.allPixelsMatching(robot)){
			if(actionNameIfMatch == null){
				return new DoneCallback();
			}
			return new ChangeToActionCallback(actionNameIfMatch);
		}else{
			if(actionNameElse == null){
				return new DoneCallback();
			}
			return new ChangeToActionCallback(actionNameElse);
		}
	}
}
