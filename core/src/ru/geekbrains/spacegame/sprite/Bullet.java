package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.base.Sprite;
import ru.geekbrains.spacegame.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 speed = new Vector2();
    private int damage;
    private Object owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public void set(
            Object owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.speed.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(speed, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public Object getOwner() {
        return owner;
    }

    public int getDamage() {
        return damage;
    }
}
