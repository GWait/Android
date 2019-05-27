package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.base.Sprite;
import ru.geekbrains.spacegame.math.Rect;

public class SpaceShip extends Sprite {

    private static float LEN = 0.01f;
    private Vector2 speed = new Vector2();
    private Vector2 nextPos = new Vector2(pos);
    private Vector2 tmp = new Vector2();

    public SpaceShip(TextureRegion region) {
        super(region);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        tmp.set(nextPos);
        if (tmp.sub(pos).len() <= LEN) {
            pos.set(nextPos);
        } else {
            pos.add(speed);
        }
    }

    @Override
    public boolean touchDown(Vector2 selectedPos, int pointer) {
        speed.set(selectedPos.cpy().sub(pos)).setLength(LEN);
        nextPos.set(selectedPos);
        return super.touchDown(selectedPos, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
        pos.set(worldBounds.pos);
    }
}
