package pixelMatching;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ExactPixelFilter implements ProbingPixel{
	public int x;
	public int y;
	public int rgb;

	public ExactPixelFilter(int x, int y, int rgb) {
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
		return buffImg.getRGB(x - screenshotArea.x, y - screenshotArea.y) != rgb;
	}
}
