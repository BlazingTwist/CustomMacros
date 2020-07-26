package keybinds;

import java.util.HashMap;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class NKeyModule implements KeyModule {
	private final HashMap<Integer, Boolean> trackedKeys = new HashMap<>();

	public NKeyModule(int... keyCodes) {
		for (int keyCode : keyCodes) {
			trackedKeys.put(keyCode, false);
		}
	}

	@Override
	public void updateModule(boolean pressed, GlobalKeyEvent e) {
		if (trackedKeys.containsKey(e.getVirtualKeyCode())) {
			trackedKeys.put(e.getVirtualKeyCode(), pressed);
		}
	}

	@Override
	public boolean isPressed() {
		return trackedKeys.values().stream().allMatch(isPressed -> isPressed);
	}
}
