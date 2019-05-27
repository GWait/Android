package ru.geekbrains.spacegame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.base.BaseScreen;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.sprite.Background;
import ru.geekbrains.spacegame.sprite.SpaceShip;

public class MainScreen extends BaseScreen {

    private Texture bg;
    private Texture ship;
    private Background background;
    private SpaceShip spaceShip;

    @Override
    public void show() {
        super.show();
        bg = new Texture("background.jpg");
        ship = new Texture("ship.png");
        background = new Background(new TextureRegion(bg));
        spaceShip = new SpaceShip(new TextureRegion(ship));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 50, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        spaceShip.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        ship.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 selectedPos, int pointer) {
        spaceShip.touchDown(selectedPos,pointer);
        return super.touchDown(selectedPos, pointer);
    }
}
