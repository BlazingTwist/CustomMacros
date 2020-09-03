package config.pixelMatching;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import utils.RGBColor;

public class ExactPixelMatch implements _ProbingPixel {
	public int x;
	public int y;
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

	@Override
	public _ProbingPixel deserialize(ObjectMapper mapper, JsonNode node) throws IOException {
		this.x = node.get("x").asInt();
		this.y = node.get("y").asInt();
		this.rgb = mapper.readValue(mapper.writeValueAsString(node.get("rgb")), RGBColor.class).toAwtColor();
		System.out.println("deserialized: " + this.toString());
		return this;
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
