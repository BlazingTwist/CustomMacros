package config.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import config.DisplayConfig;
import config.pixelMatching.PixelStateCollection;
import config.pixelMatching.ProbingPixel;
import java.io.IOException;
import java.util.ArrayList;

public class PixelStateCollectionDeserializer extends StdDeserializer<PixelStateCollection> {
	private final DisplayConfig displayConfig;

	public PixelStateCollectionDeserializer(DisplayConfig displayConfig){
		super((Class<?>)null);
		this.displayConfig = displayConfig;
	}

	@Override
	public PixelStateCollection deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		TypeReference<ArrayList<ProbingPixel>> typeRef = new TypeReference<>() {
		};
		ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
		PixelStateCollection result = new PixelStateCollection();
		ArrayList<ProbingPixel> probingPixels = mapper.readValue(jsonParser, typeRef);
		for (ProbingPixel probingPixel : probingPixels) {
			probingPixel.applyDisplayScale(displayConfig);
			result.addProbingPixel(probingPixel);
		}
		return result;
	}
}
