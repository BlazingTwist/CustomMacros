package config.pixelMatching;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import utils.RGBColor;

public class RangedPixelMatch implements _ProbingPixel {
	public int x;
	public int y;
	public RGBColor colorFrom = null;
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

	@Override
	public _ProbingPixel deserialize(ObjectMapper mapper, JsonNode node) throws IOException {
		this.x = node.get("x").asInt();
		this.y = node.get("y").asInt();
		this.colorFrom = mapper.readValue(mapper.writeValueAsString(node.get("rgbFrom")), RGBColor.class);
		this.colorTo = mapper.readValue(mapper.writeValueAsString(node.get("rgbTo")), RGBColor.class);
		System.out.println("deserialized: " + this.toString());
		return this;
	}

	@Override
	public String toString() {
		return "RangedPixelMatch{" +
				"x=" + x +
				", y=" + y +
				", colorFrom=" + colorFrom +
				", colorTo=" + colorTo +
				'}';
	}
}
