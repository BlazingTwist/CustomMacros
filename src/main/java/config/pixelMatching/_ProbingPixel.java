package config.pixelMatching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import config.deserializers.ProbingPixelDeserializer;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

@JsonDeserialize(using = ProbingPixelDeserializer.class)
public interface _ProbingPixel {
	int getX();

	int getY();

	boolean correctColor(BufferedImage buffImg, Rectangle screenshotArea);

	default int getColor(BufferedImage buffImg){
		return buffImg.getRGB(getX(), getY());
	}

	_ProbingPixel deserialize(ObjectMapper mapper, JsonNode node) throws IOException, JsonProcessingException;
}
