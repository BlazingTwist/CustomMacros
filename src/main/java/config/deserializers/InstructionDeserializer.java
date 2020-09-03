package config.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import config.instructions.TapKey;
import config.instructions._Instruction;
import config.instructions._InstructionType;
import java.io.IOException;

public class InstructionDeserializer extends StdDeserializer<_Instruction> {
	/**
	 * Required by Jackson
	 */
	public InstructionDeserializer() {
		this(null);
	}

	protected InstructionDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public _Instruction deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
		ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
		JsonNode node = mapper.readTree(jsonParser);
		System.out.println("node = " + mapper.writeValueAsString(node));
		JsonNode typeNode = node.get("type");
		if(typeNode == null){
			System.out.println("typeNode is null?!");
			throw new RuntimeException();
		}
		_InstructionType type = _InstructionType.fromString(node.get("type").asText());
		//_Instruction result = jsonParser.readValueAs(type.getClassRepresentation());
		_Instruction result = deserializationContext.readValue(node.traverse(), type.getClassRepresentation());
		//_Instruction result = mapper.readValue(mapper.writeValueAsString(node), type.getClassRepresentation());

		if(result == null){
			System.out.println("result is null??? | type = " + type.getName());
		}

		if(type == _InstructionType.TAP_KEY){
			TapKey tapKey = (TapKey) result;
			if(tapKey == null){
				System.out.println("tapKey was null???");
			}else{
				System.out.println("keyEvent = " + tapKey.keyEvent);
			}
		}

		System.out.println("done!");
		return result;
	}
}
