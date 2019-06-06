package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.spacegame.base.StyledButton;
import ru.geekbrains.spacegame.screen.GameScreen;

public class NewGameButton extends StyledButton {

    private GameScreen gameScreen;
    private Game game;

    public NewGameButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
        setHeightProportion(0.07f);
        setBottom(-0.1f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
