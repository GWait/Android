package ru.geekbrains.spacegame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.base.BaseScreen;

public class MainScreen extends BaseScreen {
    private SpriteBatch batch;
    private Texture background;
    private Texture ship;
    private Vector2 speed;
    private Vector2 position;
    private Vector2 selectedPos;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        background = new Texture("background.jpg");
        ship = new Texture("ship.png");
        speed = new Vector2(1f,1f);
        position = new Vector2(10,10);
        selectedPos = new Vector2(10,10);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);

        if (position.x < selectedPos.x && position.y < selectedPos.y) {
            position.sub(speed);
        }

        batch.draw(ship, position.x, position.y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ship.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        selectedPos.x = screenX;
        selectedPos.y = Gdx.graphics.getHeight() - screenY;
        Vector2 temp = position.cpy();

        Vector2 direction = temp.sub(selectedPos);
        direction.nor();
        direction.scl(speed);
        speed = direction;

        return super.touchDown(screenX, screenY, pointer, button);
    }
}
