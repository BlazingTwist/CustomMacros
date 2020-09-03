package config.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import config.pixelMatching._ProbingPixel;
import config.pixelMatching._ProbingPixelType;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProbingPixelDeserializer extends StdDeserializer<_ProbingPixel> {
	/**
	 * Required by Jackson
	 */
	public ProbingPixelDeserializer() {
		this(null);
	}

	protected ProbingPixelDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public _ProbingPixel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
		JsonNode node = mapper.readTree(jsonParser);
		_ProbingPixelType type = _ProbingPixelType.fromString(node.get("type").asText());
		try {
			Constructor<? extends _ProbingPixel> declaredConstructor = type.getClassRepresentation().getDeclaredConstructor();
			declaredConstructor.setAccessible(true);
			_ProbingPixel probingPixelInstance = declaredConstructor.newInstance();
			System.out.println("trying to deserialize: " + type.getName());
			_ProbingPixel result = probingPixelInstance.deserialize(mapper, node);
			System.out.println("done!\n");
			return result;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Unable to find Implementation for ProbingPixelType: " + type.getName(), e);
		}
	}
}
