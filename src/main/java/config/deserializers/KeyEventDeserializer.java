package config.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class KeyEventDeserializer extends StdDeserializer<Integer> {
	/**
	 * Required by Jackson
	 */
	public KeyEventDeserializer(){
		this(null);
	}

	protected KeyEventDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
		String keyEventName = jsonParser.readValueAs(String.class);
		try {
			return KeyEvent.class.getDeclaredField(keyEventName).getInt(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException("unable to access keyEvent: '" + keyEventName + "'! Check the spelling.", e);
		}
	}
}
