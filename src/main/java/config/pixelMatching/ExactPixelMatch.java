package config.pixelMatching;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import config.deserializers.ColorDeserializer;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ExactPixelMatch implements ProbingPixel {

	@JsonProperty("x")
	public int x;

	@JsonProperty("y")
	public int y;

	@JsonProperty("rgb")
	@JsonDeserialize(using = ColorDeserializer.class)
	public int rgb;

	/**
	 * Is required for instantiation when deserializing from config!
	 */
	private ExactPixelMatch() {
	}

	public ExactPixelMatch(int x, int y, int rgb) {
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
		return buffImg.getRGB(x - screenshotArea.x, y - screenshotArea.y) == rgb;
	}
}
