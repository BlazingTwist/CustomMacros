package config.pixelMatching;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ExactPixelMatch implements ProbingPixel {

	@JsonProperty("x")
	public int x;

	@JsonProperty("y")
	public int y;

	@JsonProperty("rgb")
	public RGBColor rgb;

	/**
	 * Is required for instantiation when deserializing from config!
	 */
	private ExactPixelMatch() {
	}

	public ExactPixelMatch(int x, int y, RGBColor rgb) {
		this.x = x;
		this.y = y;
		this.rgb = rgb;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public boolean correctColor(BufferedImage buffImg, Rectangle screenshotArea) {
		int colorValue = buffImg.getRGB(x - screenshotArea.x, y - screenshotArea.y);
		return rgb.equals(new RGBColor(colorValue));
	}

	@Override
	public void applyDisplayScale(DisplayConfig displayConfig) {
		this.x = displayConfig.applyXScale(this.x);
		this.y = displayConfig.applyYScale(this.y);
	}

	@Override
	public String toString() {
		return "ExactPixelMatch{" +
				"x=" + x +
				", y=" + y +
				", rgb=" + rgb +
				'}';
	}
}
