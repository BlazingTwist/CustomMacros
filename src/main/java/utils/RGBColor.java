package utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RGBColor {
	@JsonProperty("red")
	public int red = 0;

	@JsonProperty("green")
	public int green = 0;

	@JsonProperty("blue")
	public int blue = 0;

	/**
	 * required for deserialization
	 */
	private RGBColor() {
	}

	public RGBColor(int awtColor) {
		this.red = (awtColor >> 16) & 0xFF;
		this.green = (awtColor >> 8) & 0xFF;
		this.blue = awtColor & 0xFF;
	}

	public RGBColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int toAwtColor() {
		return (red << 16) + (green << 8) + blue;
	}

	@Override
	public String toString() {
		return "r: " + normalizeColorString(this.red)
				+ " | g: " + normalizeColorString(this.green)
				+ " | b: " + normalizeColorString(this.blue);
	}

	/**
	 * normalizes color-values (0-255) to __0-255
	 *
	 * @param value color-value from 0-255
	 * @return formatted color-value as __0-255
	 */
	private String normalizeColorString(int value) {
		String leadingSpaces = "";
		if (value < 100) {
			leadingSpaces += "_";
		}
		if (value < 10) {
			leadingSpaces += "_";
		}
		return leadingSpaces + value;
	}
}
