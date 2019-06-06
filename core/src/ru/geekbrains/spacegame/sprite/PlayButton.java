package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.spacegame.base.StyledButton;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.screen.GameScreen;

public class PlayButton extends StyledButton {

    private Game game;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
        setHeightProportion(0.2f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(Rect worldBounds) {
        setRight(worldBounds.getRight() - 0.03f);
        setBottom(worldBounds.getBottom() + 0.03f);
    }
}
