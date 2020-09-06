package config.keybinds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class KeyManager {
	private final HashMap<String, ArrayList<KeyModule>> keybinds;
	private final GlobalKeyboardHook keyboardHook;
	private final ArrayList<Consumer<String>> keybindCallbacks = new ArrayList<>();

	public KeyManager(HashMap<String, ArrayList<KeyModule>> keybinds) {
		this.keybinds = keybinds;

		for(Map.Entry<String, ArrayList<KeyModule>> entry : keybinds.entrySet()){
			System.out.println("got keybind: " + entry.getKey() + " | keybinds: " + entry.getValue().stream().map(Object::toString).collect(Collectors.joining(", ")));
		}

		keyboardHook = new GlobalKeyboardHook(true);

		System.out.println("Keyboard hook initialized! Connected Keyboards:");
		for (Entry<Long, String> keyboard : GlobalKeyboardHook.listKeyboards().entrySet()) {
			System.out.println("\t" + keyboard.getKey() + ": " + keyboard.getValue());
		}

		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override
			public void keyPressed(GlobalKeyEvent event) {
				updateKeybinds(true, event);
			}

			@Override
			public void keyReleased(GlobalKeyEvent event) {
				updateKeybinds(false, event);
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
	}

	public boolean isModulePressed(String name) {
		return keybinds.get(name).stream().allMatch(KeyModule::isPressed);
	}

	public void addKeybindCallback(Consumer<String> callback) {
		this.keybindCallbacks.add(callback);
	}

	public void removeKeybindCallback(Consumer<String> callback) {
		this.keybindCallbacks.remove(callback);
	}

	private void updateKeybinds(boolean pressed, GlobalKeyEvent e) {
		for (Entry<String, ArrayList<KeyModule>> binding : keybinds.entrySet()) {
			binding.getValue().forEach(module -> module.updateModule(pressed, e));
			if (binding.getValue().stream().allMatch(KeyModule::isPressed)) {
				triggerKeybindCallbacks(binding.getKey());
			}
		}
	}

	private void triggerKeybindCallbacks(String keybind) {
		keybindCallbacks.forEach(consumer -> consumer.accept(keybind));
	}

	public void shutdown() {
		if (keyboardHook != null) {
			keyboardHook.shutdownHook();
		}
	}
}
