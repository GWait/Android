package ru.geekbrains.spacegame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.base.BaseScreen;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.sprite.*;

public class MainScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private Game game;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] stars;

    private PlayButton playButton;
    private ExitButton exitButton;

    public MainScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("background.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        playButton = new PlayButton(atlas, game);
        exitButton = new ExitButton(atlas, game);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 50, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        playButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
        update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        playButton.resize(worldBounds);
        exitButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 selectedPos, int pointer) {
        playButton.touchDown(selectedPos, pointer);
        exitButton.touchDown(selectedPos, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 selectedPos, int pointer) {
        playButton.touchUp(selectedPos, pointer);
        exitButton.touchUp(selectedPos, pointer);
        return false;
    }
}
