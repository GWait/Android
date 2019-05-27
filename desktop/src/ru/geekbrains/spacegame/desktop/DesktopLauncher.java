package ru.geekbrains.spacegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.geekbrains.spacegame.SpaceGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		float aspect = 3/4f;
		config.width = 400;
		config.height = (int) (config.width / aspect);
		config.resizable = false;
		new LwjglApplication(new SpaceGame(), config);
	}
}
