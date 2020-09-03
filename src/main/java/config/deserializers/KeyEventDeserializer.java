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
		System.out.println("HEY THERE");
		String keyEventName = jsonParser.readValueAs(String.class);
		try {
			int val = KeyEvent.class.getDeclaredField(keyEventName).getInt(null);
			System.out.println("deserialized " + keyEventName + " to " + val);
			return val;
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException("unable to access keyEvent: '" + keyEventName + "'! Check the spelling.", e);
		}
	}
}
