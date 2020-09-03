package config.pixelMatching;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum _ProbingPixelType {
	EXACT_PIXEL_MATCH("ExactPixelMatch", ExactPixelMatch.class),
	EXACT_PIXEL_FILTER("ExactPixelFilter", ExactPixelFilter.class),
	RANGED_PIXEL_MATCH("RangedPixelMatch", RangedPixelMatch.class),
	RANGED_PIXEL_FILTER("RangedPixelFilter", RangedPixelFilter.class);

	private final String name;
	private final Class<? extends _ProbingPixel> classRepresentation;

	_ProbingPixelType(String name, Class<? extends _ProbingPixel> classRepresentation) {
		this.name = name;
		this.classRepresentation = classRepresentation;
	}

	public String getName() {
		return name;
	}

	public Class<? extends _ProbingPixel> getClassRepresentation() {
		return classRepresentation;
	}

	@JsonCreator
	public static _ProbingPixelType fromString(@JsonProperty("type") String name) {
		for (_ProbingPixelType type : _ProbingPixelType.values()) {
			if (type.name.equalsIgnoreCase(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown ProbingPixelType: " + name);
	}
}
