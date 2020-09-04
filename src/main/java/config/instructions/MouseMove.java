package config.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.DoneCallback;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

public class MouseMove implements Instruction {

	@JsonProperty("startX")
	private int startX;

	@JsonProperty("startY")
	private int startY;

	@JsonProperty("endX")
	private int endX;

	@JsonProperty("endY")
	private int endY;

	@JsonProperty("duration")
	private int duration;

	@Override
	public InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig) {
		long startTime = System.currentTimeMillis();

		// arbitrary amount, but 200 steps should be plenty enough for smooth movement
		// this is primarily to prevent excessive memory usage for very slow mouse movement
		int stepCount = Math.min(duration / 4, 200);
		double stepTime = (double) duration / (double) stepCount;

		double startX = (double) this.startX * displayConfig.getRelativeXFactor();
		double startY = (double) this.startY * displayConfig.getRelativeYFactor();
		double endX = (double) this.endX * displayConfig.getRelativeXFactor();
		double endY = (double) this.endY * displayConfig.getRelativeYFactor();
		double stepOffset = (double) (stepCount - 1); //-1 so that we actually finish on endX and endY

		for (int i = 0; i < stepCount; i++) {
			long targetTime = (long) (startTime + (i * stepTime));
			long currentTime = System.currentTimeMillis();
			long timeToSpare = targetTime - currentTime;
			if (timeToSpare > 0) {
				// great, we're ahead of schedule
				try {
					Thread.sleep(timeToSpare);
				} catch (InterruptedException e) {
					// spare time will carry over into next step, no harm done, ignore
				}
			} else if (timeToSpare < 0) {
				// we'll have to skip some steps to save time
				int skipCount = (int) ((double) Math.abs(timeToSpare) / stepTime);
				i += skipCount;
			}

			double targetX = startX + ((double) i * (endX - startX) / stepOffset);
			double targetY = startY + ((double) i * (endY - startY) / stepOffset);
			robot.mouseMove((int) targetX, (int) targetY);
		}

		return new DoneCallback();
	}
}
