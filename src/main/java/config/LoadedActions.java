package config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import config.instructions.Instruction;
import java.util.ArrayList;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadedActions {
	@JsonProperty("actions")
	public HashMap<String, HashMap<String, ArrayList<Instruction>>> actions;
}
