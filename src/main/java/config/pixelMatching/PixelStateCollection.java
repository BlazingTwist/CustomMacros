package config.pixelMatching;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PixelStateCollection {
	private final ArrayList<ProbingPixel> probingPixels = new ArrayList<>();
	private Rectangle targetRectangle = null;

	public PixelStateCollection addProbingPixel(ProbingPixel probingPixel) {
		probingPixels.add(probingPixel);
		updateScreenshotRectangle(probingPixel);
		return this;
	}

	public BufferedImage takeScreenshot(Robot robot) {
		robot.mouseMove(0, 0);
		return robot.createScreenCapture(targetRectangle);
	}

	public boolean allPixelsMatching(Robot robot) {
		return allPixelsMatching(takeScreenshot(robot));
	}

	public boolean allPixelsMatching(BufferedImage buffImg) {
		return probingPixels
				.stream()
				.allMatch(probingPixel -> probingPixel.correctColor(buffImg, targetRectangle));
	}

	private void updateScreenshotRectangle(ProbingPixel probingPixel) {
		if (targetRectangle == null) {
			targetRectangle = new Rectangle(probingPixel.getX(), probingPixel.getY(), 1, 1);
			return;
		}

		int targetMaxX = targetRectangle.x + targetRectangle.width - 1;
		int targetMaxY = targetRectangle.y + targetRectangle.height - 1;

		if (targetRectangle.x > probingPixel.getX()) {
			//probing pixel is outside left bound
			//move left bound outwards and update width
			int delta = targetRectangle.x - probingPixel.getX();
			targetRectangle.x -= delta;
			targetRectangle.width += delta;
		} else if (targetMaxX < probingPixel.getX()) {
			//probing pixel is outside right bound
			//increase width
			int delta = probingPixel.getX() - targetMaxX;
			targetRectangle.width += delta;
		}

		if (targetRectangle.y > probingPixel.getY()) {
			//probing pixel is outside upper bound
			//move upper bound and update height
			int delta = targetRectangle.y - probingPixel.getY();
			targetRectangle.y -= delta;
			targetRectangle.height += delta;
		} else if (targetMaxY < probingPixel.getY()) {
			//probing pixel is outside lower bound
			//increase height
			int delta = probingPixel.getY() - targetMaxY;
			targetRectangle.height += delta;
		}
	}

	@Override
	public String toString() {
		return "PixelStateCollection{" +
				"probingPixels=" + probingPixels.stream().map(Object::toString).collect(Collectors.joining(", ")) +
				", targetRectangle=" + targetRectangle +
				'}';
	}
}
