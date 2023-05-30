package core.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
	
	public static void main(String[] args) {

		Lwjgl3ApplicationConfiguration cfg =
				new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("Asteroids");
		cfg.setWindowedMode(1000, 800);
		cfg.setResizable(false);
		new Lwjgl3Application(new Game(), cfg);

	}
	
}
