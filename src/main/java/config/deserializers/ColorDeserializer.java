package config.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import utils.RGBColor;

public class ColorDeserializer extends StdDeserializer<Integer> {
	/**
	 * Required by Jackson
	 */
	public ColorDeserializer() {
		this(null);
	}

	protected ColorDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
		return jsonParser.readValueAs(RGBColor.class).toAwtColor();
	}
}
