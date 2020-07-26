package keybinds;

import lc.kra.system.keyboard.event.GlobalKeyEvent;

public interface KeyModule {
	void updateModule(boolean pressed, GlobalKeyEvent e);

	boolean isPressed();
}
