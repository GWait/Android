package ru.geekbrains.spacegame;

import com.badlogic.gdx.Game;
import ru.geekbrains.spacegame.screen.MainScreen;

public class SpaceGame extends Game {

	@Override
	public void create() {
		setScreen(new MainScreen());
	}
}
