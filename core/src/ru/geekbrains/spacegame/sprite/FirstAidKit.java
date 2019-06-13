package ru.geekbrains.spacegame.sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.math.Rect;

public class FirstAidKit extends SpaceItem {
    private Rect worldBounds;
    private Vector2 speed = new Vector2(0f, -0.1f);
    private int hp;

    public FirstAidKit() {
        regions = new TextureRegion[1];
    }

    public void set(
            TextureRegion region,
            float height,
            Rect worldBounds,
            int hp
    ) {
        this.regions[0] = region;
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.hp = hp;
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(speed, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public int getHp() {
        return hp;
    }
}
