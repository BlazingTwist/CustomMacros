package config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains all the keybindNames that are mapped to flowControl
 */
public class FlowControlConfig {

	@JsonProperty("pauseAction")
	private String pauseAction = null;

	@JsonProperty("unpauseAction")
	private String unpauseAction = null;

	@JsonProperty("quitModule")
	private String quitModule = null;

	@JsonProperty("exit")
	private String exit = null;

	public String getPauseAction() {
		return pauseAction;
	}

	public String getUnpauseAction() {
		return unpauseAction;
	}

	public String getQuitModule() {
		return quitModule;
	}

	public String getExit() {
		return exit;
	}
}
