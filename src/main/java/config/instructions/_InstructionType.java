package config.instructions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum _InstructionType {
	TAP_KEY("TapKey", TapKey.class),
	WAIT("Wait", Wait.class),
	CHANGE_TO_ACTION("ChangeToAction", ChangeToAction.class),
	PROBE_SCREEN("ProbeScreen", ProbeScreen.class),
	WAIT_FOR_PIXEL_STATE("WaitForPixelState", WaitForPixelState.class),
	REPEAT("Repeat", Repeat.class),
	RUN_ACTION("RunAction", RunAction.class);


	private final String name;
	private final Class<? extends _Instruction> classRepresentation;

	_InstructionType(String name, Class<? extends _Instruction> classRepresentation) {
		this.name = name;
		this.classRepresentation = classRepresentation;
	}

	public String getName() {
		return name;
	}

	public Class<? extends _Instruction> getClassRepresentation() {
		return classRepresentation;
	}

	@JsonCreator
	public static _InstructionType fromString(@JsonProperty("type") String name) {
		for (_InstructionType type : _InstructionType.values()) {
			if (type.name.equalsIgnoreCase(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown InstructionType: " + name);
	}
}
