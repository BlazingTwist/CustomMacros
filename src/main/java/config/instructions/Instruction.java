package config.instructions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import config.DisplayConfig;
import config.LoadedConfigCore;
import config.instructions.callbacks.InstructionCallback;
import java.awt.Robot;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = ChangeToAction.class, name = "changeToAction"),
		@JsonSubTypes.Type(value = ExecCommand.class, name = "execCommand"),
		@JsonSubTypes.Type(value = LogMessage.class, name = "logMessage"),
		@JsonSubTypes.Type(value = MouseClick.class, name = "mouseClick"),
		@JsonSubTypes.Type(value = MouseMove.class, name = "mouseMove"),
		@JsonSubTypes.Type(value = MousePress.class, name = "mousePress"),
		@JsonSubTypes.Type(value = MouseRelease.class, name = "mouseRelease"),
		@JsonSubTypes.Type(value = MouseSnap.class, name = "mouseSnap"),
		@JsonSubTypes.Type(value = PressKey.class, name = "pressKey"),
		@JsonSubTypes.Type(value = ProbeScreen.class, name = "probeScreen"),
		@JsonSubTypes.Type(value = ReleaseKey.class, name = "releaseKey"),
		@JsonSubTypes.Type(value = Repeat.class, name = "repeat"),
		@JsonSubTypes.Type(value = RunAction.class, name = "runAction"),
		@JsonSubTypes.Type(value = TapKey.class, name = "tapKey"),
		@JsonSubTypes.Type(value = TestKeybind.class, name = "testKeybind"),
		@JsonSubTypes.Type(value = Wait.class, name = "wait"),
		@JsonSubTypes.Type(value = WaitForKeybind.class, name = "waitForKeybind"),
		@JsonSubTypes.Type(value = WaitForPixelState.class, name = "waitForPixelState")
})
public interface Instruction {
	InstructionCallback run(Robot robot, LoadedConfigCore configCore, DisplayConfig displayConfig);
}
