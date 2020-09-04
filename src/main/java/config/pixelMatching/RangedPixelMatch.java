package config.pixelMatching;

import com.fasterxml.jackson.annotation.JsonProperty;
import config.DisplayConfig;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class RangedPixelMatch implements ProbingPixel {

	@JsonProperty("x")
	public int x;

	@JsonProperty("y")
	public int y;

	@JsonProperty("rgbFrom")
	public RGBColor rgbFrom = null;

	@JsonProperty("rgbTo")
	public RGBColor rgbTo = null;

	/**
	 * Is required for instantiation when deserializing from config!
	 */
	private RangedPixelMatch() {
	}

	public RangedPixelMatch(int x, int y, RGBColor rgbFrom, RGBColor rgbTo) {
		this.x = x;
		this.y = y;
		this.rgbFrom = rgbFrom;
		this.rgbTo = rgbTo;
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
		RGBColor color = new RGBColor(buffImg.getRGB(x - screenshotArea.x, y - screenshotArea.y));
		return rgbFrom.red <= color.red && rgbFrom.green <= color.green && rgbFrom.blue <= color.blue
				&& rgbTo.red >= color.red && rgbTo.green >= color.green && rgbTo.blue >= color.blue;
	}

	@Override
	public void applyDisplayScale(DisplayConfig displayConfig) {
		this.x = displayConfig.applyXScale(this.x);
		this.y = displayConfig.applyYScale(this.y);
	}

	@Override
	public String toString() {
		return "RangedPixelMatch{" +
				"x=" + x +
				", y=" + y +
				", colorFrom=" + rgbFrom +
				", colorTo=" + rgbTo +
				'}';
	}
}
