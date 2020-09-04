package config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OnStuckConfig {

	@JsonProperty("delay")
	private long delay;

	@JsonProperty("module")
	private String module;

	@JsonProperty("action")
	private String action;

	public long getDelay() {
		return delay;
	}

	public String getModule() {
		return module;
	}

	public String getAction() {
		return action;
	}
}
