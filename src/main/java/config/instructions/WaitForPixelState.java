package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.InstructionCallback;
import config.pixelMatching.PixelStateCollection;
import java.awt.Robot;

public class WaitForPixelState implements Instruction {

	@JsonProperty("pixelStateCollection")
	PixelStateCollection pixelStateCollection = null;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		while(!pixelStateCollection.allPixelsMatching(robot));
		return new DoneCallback();
	}
}
