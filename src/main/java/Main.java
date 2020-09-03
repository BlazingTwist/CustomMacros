import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import config.LoadedActions;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import keybinds.KeyManager;

public class Main {
	public static void main(String[] args) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.err.println("Failed to initialize Robot! Shutting down...");
			return;
		}

		LoadedActions actions;
		URL configUrl = Thread.currentThread().getContextClassLoader().getResource("testConfig3.conf");

		if (configUrl == null) {
			System.err.println("Failed to find configFile!");
			return;
		}

		try {
			File configFile = Paths.get(configUrl.toURI()).toFile();
			if (!configFile.exists() || !configFile.isFile()) {
				System.err.println("Unable to find configFile!");
				return;
			}

			Config config = ConfigFactory.parseFile(configFile);
			if (!config.hasPath("actions")) {
				System.err.println("unable to find 'actions' in config! make sure it exists.");
				return;
			}

			String jsonString = config.resolve().root().render(ConfigRenderOptions.concise());
			System.out.println(jsonString);

			ObjectMapper mapper = new ObjectMapper();
			actions = mapper.readValue(jsonString, LoadedActions.class);
		} catch (URISyntaxException e) {
			System.err.println("Failed to find configFile!");
			e.printStackTrace();
			return;
		} catch (JsonProcessingException e) {
			System.err.println("Failed to parse configFile!");
			e.printStackTrace();
			return;
		}

		KeyManager keyManager = new KeyManager();
		keyManager.prepare();

		runMacros(robot, keyManager, actions);

		keyManager.shutdown();
	}

	public static void runMacros(Robot robot, KeyManager keyManager, LoadedActions actions) {

	}
}
