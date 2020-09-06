package config.keybinds;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import config.deserializers.KeyEventDeserializer;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class KeyDownModule implements KeyModule {

	@JsonProperty("keyEvent")
	@JsonDeserialize(using = KeyEventDeserializer.class)
	@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
	private int keyEvent = 0;

	@JsonIgnore
	private boolean pressed = false;

	@Override
	public void updateModule(boolean pressed, GlobalKeyEvent e) {
		if(keyEvent == e.getVirtualKeyCode()){
			this.pressed = pressed;
		}
	}

	@Override
	public boolean isPressed() {
		return pressed;
	}

	@Override
	public String toString() {
		return "KeyDownModule{" +
				"keyEvent=" + keyEvent +
				'}';
	}
}
