package pixelMatching;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface ProbingPixel {
	int getX();

	int getY();

	boolean correctColor(BufferedImage buffImg, Rectangle screenshotArea);

	default int getColor(BufferedImage buffImg){
		return buffImg.getRGB(getX(), getY());
	}
}
