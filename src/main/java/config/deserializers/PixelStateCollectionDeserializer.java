package config.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import config.pixelMatching.PixelStateCollection;
import config.pixelMatching.ProbingPixel;
import java.io.IOException;
import java.util.ArrayList;

public class PixelStateCollectionDeserializer extends StdDeserializer<PixelStateCollection> {
	/**
	 * Required by Jackson
	 */
	public PixelStateCollectionDeserializer(){
		this(null);
	}

	protected PixelStateCollectionDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public PixelStateCollection deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		TypeReference<ArrayList<ProbingPixel>> typeRef = new TypeReference<>() {
		};
		ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
		PixelStateCollection result = new PixelStateCollection();
		mapper.readValue(jsonParser, typeRef).forEach(result::addProbingPixel);
		return result;
	}
}
