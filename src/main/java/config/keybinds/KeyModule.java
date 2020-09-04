package config.keybinds;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = KeyDownModule.class, name = "keyDown"),
		@JsonSubTypes.Type(value = KeyUpModule.class, name = "keyUp"),
		@JsonSubTypes.Type(value = OnKeyDownModule.class, name = "onKeyDown"),
		@JsonSubTypes.Type(value = OnKeyUpModule.class, name = "onKeyUp")
})
public interface KeyModule {
	void updateModule(boolean pressed, GlobalKeyEvent e);

	boolean isPressed();
}
