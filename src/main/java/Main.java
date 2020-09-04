import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import config.DisplayConfig;
import config.FlowControlConfig;
import config.LoadedConfigCore;
import config.deserializers.PixelStateCollectionDeserializer;
import config.instructions.Instruction;
import config.instructions.callbacks.ChangeToActionCallback;
import config.instructions.callbacks.EncounteredExceptionCallback;
import config.instructions.callbacks.InsertActionCallback;
import config.instructions.callbacks.InstructionCallback;
import config.pixelMatching.PixelStateCollection;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import config.keybinds.Hotkey;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.prepare();
	}

	Main() {
		instance = this;
	}

	private final Main instance;
	private LoadedConfigCore configCore = null;
	private DisplayConfig displayConfig = null;
	private boolean paused = false;
	private String activeModule = null;
	private final ArrayList<Instruction> activeInstructions = new ArrayList<>();
	private Timer inactiveShutdownTimer = null;

	private void prepare() {
		Robot robot;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.err.println("Failed to initialize Robot!");
			return;
		}

		LoadedConfigCore core;
		try {
			Config scriptConfig = ConfigFactory.parseResources("testConfig2.conf");
			Config coreConfig = ConfigFactory.parseResources("testCoreConfig.conf")
					.withFallback(scriptConfig);
			Config resolvedConfig = coreConfig.resolve();
			String displayConfigJson = resolvedConfig.getConfig("displayConfig").root().render(ConfigRenderOptions.concise());
			String coreConfigJson = resolvedConfig.root().render(ConfigRenderOptions.concise());
			System.out.println("displayConfig: " + displayConfigJson);
			System.out.println("coreConfig: " + coreConfigJson);

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES);

			displayConfig = mapper.readValue(displayConfigJson, DisplayConfig.class);

			SimpleModule pixelStateCollectionModule = new SimpleModule();
			pixelStateCollectionModule.addDeserializer(PixelStateCollection.class, new PixelStateCollectionDeserializer(displayConfig));
			mapper.registerModule(pixelStateCollectionModule);

			core = mapper.readValue(coreConfigJson, LoadedConfigCore.class);
			core.getKeyManager().addKeybindCallback(this::keybindCallback);
		} catch (JsonProcessingException e) {
			System.err.println("Failed to parse configFile!");
			e.printStackTrace();
			return;
		}

		configCore = core;
		waitForHotkey(robot);

		core.getKeyManager().shutdown();
	}

	/**
	 * Triggered when flowControl.pauseAction is pressed
	 */
	private void onPause() {
		if (!paused) {
			System.out.println("Pausing, the next instruction will wait for you to unpause.");
			paused = true;
			stopShutdownTimer();
		} else {
			System.out.println("Already paused.");
		}
	}

	/**
	 * Triggered when flowControl.unpauseAction is pressed
	 */
	private void onUnPause() {
		if (paused) {
			System.out.println("Un-paused.");
			paused = false;
			synchronized (instance) {
				instance.notify();
			}
		} else {
			System.out.println("Already unpaused.");
		}
	}

	private void restartShutdownTimer() {
		if (configCore.getOnStuckConfig() == null) {
			// no timer configured
			return;
		}
		stopShutdownTimer();

		inactiveShutdownTimer = new Timer();
		inactiveShutdownTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				changeToAction(configCore.getOnStuckConfig().getModule(), configCore.getOnStuckConfig().getAction());
			}
		}, configCore.getOnStuckConfig().getDelay());
	}

	private void stopShutdownTimer(){
		if (inactiveShutdownTimer != null) {
			inactiveShutdownTimer.cancel();
			inactiveShutdownTimer.purge();
			inactiveShutdownTimer = null;
		}
	}

	/**
	 * used to trigger hotkeys and flowcontrol
	 *
	 * @param keybind name of keybind
	 */
	private void keybindCallback(String keybind) {
		FlowControlConfig flowControl = configCore.getFlowControl();
		if (keybind.equalsIgnoreCase(flowControl.getPauseAction())) {
			onPause();
		}
		if (keybind.equalsIgnoreCase(flowControl.getUnpauseAction())) {
			onUnPause();
		}
		if (keybind.equalsIgnoreCase(flowControl.getQuitModule())) {
			System.out.println("got quitModule keybind.");
			activeInstructions.clear();
			activeModule = null;
			stopShutdownTimer();
		}
		if (keybind.equalsIgnoreCase(flowControl.getExit())) {
			System.out.println("got exit keybind.");
			configCore.getKeyManager().shutdown();
			stopShutdownTimer();
			System.exit(-1);
		}

		for (Hotkey hotkey : configCore.getHotkeys()) {
			if (!keybind.equalsIgnoreCase(hotkey.getKeybind())) {
				continue;
			}
			if (!activeInstructions.isEmpty() && !hotkey.isGlobal()) {
				// currently executing instructions, keybind would have to be global to trigger
				continue;
			}
			changeToAction(hotkey.getModule(), hotkey.getAction());
		}
	}

	private void waitForHotkey(Robot robot) {
		while (true) {
			System.out.println("Waiting for an Action to start, your hotkeys:");
			printHotkeys();
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.err.println("Waiting for hotkey got interrupted! shutdown...");
					e.printStackTrace();
					return;
				}
			}
			if (!activeInstructions.isEmpty()) {
				runInstructions(robot);
			} else {
				System.out.println("got notified but still no instructions, what the heck?");
			}
		}
	}

	private void printHotkeys() {
		for (Hotkey hotkey : configCore.getHotkeys()) {
			System.out.println("\tkeybind: "
					+ hotkey.getKeybind()
					+ " | triggers: "
					+ hotkey.getModule()
					+ "/" + hotkey.getAction());
		}
	}

	private void changeToAction(String moduleName, String actionName) {
		activeInstructions.clear();

		HashMap<String, HashMap<String, ArrayList<Instruction>>> actions = configCore.getActions();
		if (!actions.containsKey(moduleName)) {
			String message = "Can't change to unknown module: "
					+ moduleName
					+ " | available modules: "
					+ String.join(", ", actions.keySet());
			throw new RuntimeException(message);
		}

		HashMap<String, ArrayList<Instruction>> module = actions.get(moduleName);
		if (!module.containsKey(actionName)) {
			String message = "Can't change to unknown action: "
					+ actionName
					+ " | available actions: "
					+ String.join(", ", module.keySet());
			throw new RuntimeException(message);
		}

		activeInstructions.addAll(module.get(actionName));
		activeModule = moduleName;
		synchronized (instance) {
			instance.notify();
		}
	}

	private void runInstructions(Robot robot) {
		/*XStream xStream = new XStream(new Sun14ReflectionProvider(
				new FieldDictionary(new ImmutableFieldKeySorter())),
				new DomDriver("utf-8"));
		System.out.println(xStream.toXML(core));*/

		// TODO figure out how to make executable
		// TODO drag and drop script(s) to executable to run them
		// TODO drag and drop folder(s) to executable to run all scripts within them
		// TODO update documentation to inform on how to run scripts
		// TODO update scripts

		while (!activeInstructions.isEmpty()) {
			if (paused) {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						System.err.println("Waiting for unpause got interrupted! clearing instructions");
						e.printStackTrace();
						activeInstructions.clear();
						activeModule = null;
						return;
					}
				}
			}

			restartShutdownTimer();

			Instruction instruction = activeInstructions.get(0);
			activeInstructions.remove(0);

			InstructionCallback callback = instruction.run(robot, configCore, displayConfig);

			switch (callback.getCallbackType()) {
				case DONE:
					//great! nothing to do here
					break;
				case INSERT_ACTION: {
					//instruction requires the execution of another action
					InsertActionCallback castedCallback = (InsertActionCallback) callback;
					activeInstructions.addAll(0, castedCallback.getAction());
					break;
				}
				case CHANGE_TO_ACTION: {
					ChangeToActionCallback castedCallback = (ChangeToActionCallback) callback;
					changeToAction(activeModule, castedCallback.getActionName());
					break;
				}
				case ENCOUNTERED_EXCEPTION: {
					EncounteredExceptionCallback castedCallback = (EncounteredExceptionCallback) callback;

					throw new RuntimeException("Instruction encountered exception!", castedCallback.getException());
				}
			}
		}

		stopShutdownTimer();
	}
}
