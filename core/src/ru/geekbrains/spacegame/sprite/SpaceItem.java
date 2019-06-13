package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.base.Sprite;
import ru.geekbrains.spacegame.math.Rect;

public abstract class SpaceItem extends Sprite {

    protected Vector2 speed;
    protected Vector2 v0;
    protected Rect worldBounds;

    public SpaceItem(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public SpaceItem() { }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public boolean isPlayerCollision(Rect spaceShip) {
        return !(
                spaceShip.getRight() < getLeft() ||
                        spaceShip.getLeft() > getRight() ||
                        spaceShip.getBottom() > getTop() ||
                        spaceShip.getTop() < pos.y
        );
    }
}
