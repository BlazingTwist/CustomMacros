package config.keybinds;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hotkey {

	@JsonProperty("keybind")
	private String keybind = null;

	@JsonProperty("global")
	private boolean global = false;

	@JsonProperty("module")
	private String module = null;

	@JsonProperty("action")
	private String action = null;

	public String getKeybind() {
		return keybind;
	}

	public boolean isGlobal() {
		return global;
	}

	public String getModule() {
		return module;
	}

	public String getAction() {
		return action;
	}
}
