package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.spacegame.base.StyledButton;
import ru.geekbrains.spacegame.math.Rect;

public class ExitButton extends StyledButton {

    private Game game;

    public ExitButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btExit"));
        this.game = game;
        setHeightProportion(0.18f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setLeft(worldBounds.getLeft() + 0.03f);
        setBottom(worldBounds.getBottom() + 0.03f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
