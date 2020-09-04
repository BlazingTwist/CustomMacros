package config.pixelMatching;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import config.DisplayConfig;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = ExactPixelMatch.class, name = "exactPixelMatch"),
		@JsonSubTypes.Type(value = ExactPixelFilter.class, name = "exactPixelFilter"),
		@JsonSubTypes.Type(value = RangedPixelMatch.class, name = "rangedPixelMatch"),
		@JsonSubTypes.Type(value = RangedPixelFilter.class, name = "rangedPixelFilter")
})
public interface ProbingPixel {
	int getX();

	int getY();

	boolean correctColor(BufferedImage buffImg, Rectangle screenshotArea);

	default int getColor(BufferedImage buffImg){
		return buffImg.getRGB(getX(), getY());
	}

	void applyDisplayScale(DisplayConfig displayConfig);
}
