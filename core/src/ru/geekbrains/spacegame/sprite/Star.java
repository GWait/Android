package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.base.Sprite;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.math.Rnd;

public class Star extends Sprite {

    private Vector2 speed;
    private Rect worldBounds;
    private float height;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        float speedX = Rnd.nextFloat(-0.005f, 0.005f);
        float speedY = Rnd.nextFloat(-0.5f, -0.1f);
        speed = new Vector2(speedX, speedY);
        height = Rnd.nextFloat(0.009f, 0.012f);
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        if (height >= 0.015f) {
            height = 0.009f;
        } else {
            height += 0.0001f;
        }
        setHeightProportion(height);
        pos.mulAdd(speed, delta);
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }
}
