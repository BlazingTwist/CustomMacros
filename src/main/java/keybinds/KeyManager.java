package keybinds;

import java.util.HashMap;
import java.util.Map.Entry;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class KeyManager {
	private final HashMap<String, KeyModule> keyModules = new HashMap<>();
	public GlobalKeyboardHook keyboardHook = null;

	public void prepare() {
		keyboardHook = new GlobalKeyboardHook(true);

		System.out.println("Keyboard hook initialized! Connected Keyboards:");
		for (Entry<Long, String> keyboard : GlobalKeyboardHook.listKeyboards().entrySet()) {
			System.out.println("\t" + keyboard.getKey() + ": " + keyboard.getValue());
		}

		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override
			public void keyPressed(GlobalKeyEvent event) {
				updateKeyModule(true, event);
			}

			@Override
			public void keyReleased(GlobalKeyEvent event) {
				updateKeyModule(false, event);
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
	}

	public boolean isModulePressed(String name) {
		return keyModules.get(name).isPressed();
	}

	public void addKeyModule(String name, KeyModule module) {
		keyModules.put(name, module);
	}

	public void removeKeyModule(String name) {
		keyModules.remove(name);
	}

	private void updateKeyModule(boolean pressed, GlobalKeyEvent e) {
		keyModules.values().forEach(module -> module.updateModule(pressed, e));
	}

	public void shutdown() {
		if (keyboardHook != null) {
			keyboardHook.shutdownHook();
		}
	}
}
