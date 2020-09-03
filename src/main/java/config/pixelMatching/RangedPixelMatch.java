package config.pixelMatching;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import utils.RGBColor;

public class RangedPixelMatch implements ProbingPixel {

	@JsonProperty("x")
	public int x;

	@JsonProperty("y")
	public int y;

	@JsonProperty("rgbFrom")
	public RGBColor colorFrom = null;

	@JsonProperty("rgbTo")
	public RGBColor colorTo = null;

	/**
	 * Is required for instantiation when deserializing from config!
	 */
	private RangedPixelMatch() {
	}

	public RangedPixelMatch(int x, int y, RGBColor colorFrom, RGBColor colorTo) {
		this.x = x;
		this.y = y;
		this.colorFrom = colorFrom;
		this.colorTo = colorTo;
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
		return colorFrom.red <= color.red && colorFrom.green <= color.green && colorFrom.blue <= color.blue
				&& colorTo.red >= color.red && colorTo.green >= color.green && colorTo.blue >= color.blue;
	}
}
