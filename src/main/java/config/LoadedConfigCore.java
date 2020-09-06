package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import config.instructions.Instruction;
import java.util.ArrayList;
import java.util.HashMap;
import config.keybinds.Hotkey;
import config.keybinds.KeyManager;
import config.keybinds.KeyModule;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadedConfigCore {

	@JsonProperty("actions")
	private HashMap<String, HashMap<String, ArrayList<Instruction>>> actions;

	private KeyManager keyManager;

	@JsonProperty("hotkeys")
	private HashMap<String, Hotkey> hotkeys;

	@JsonProperty("flowControl")
	private FlowControlConfig flowControl;

	@JsonProperty("onStuck")
	private OnStuckConfig onStuckConfig = null;

	@JsonCreator
	public LoadedConfigCore(@JsonProperty("keybinds") HashMap<String, ArrayList<KeyModule>> keybinds){
		keyManager = new KeyManager(keybinds);
	}

	public HashMap<String, HashMap<String, ArrayList<Instruction>>> getActions() {
		return actions;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public HashMap<String, Hotkey> getHotkeys() {
		return hotkeys;
	}

	public FlowControlConfig getFlowControl() {
		return flowControl;
	}

	public OnStuckConfig getOnStuckConfig() {
		return onStuckConfig;
	}
}
