package ru.geekbrains.spacegame.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.math.Rnd;
import ru.geekbrains.spacegame.pool.ItemsPool;
import ru.geekbrains.spacegame.sprite.FirstAidKit;

public class ItemsGenerator {
    private TextureRegion region = new TextureRegion(new Texture("firstaidkit.png"));
    private Rect worldBounds;
    private ItemsPool pool;

    public ItemsGenerator(Rect worldBounds, ItemsPool pool) {
        this.worldBounds = worldBounds;
        this.pool = pool;
    }

    public void generate() {
        FirstAidKit kit = pool.obtain();
        kit.set(region, 0.07f, worldBounds, 10);

        kit.pos.x = Rnd.nextFloat(worldBounds.getLeft() + kit.getHalfWidth(), worldBounds.getRight() - kit.getHalfWidth());
        kit.setBottom(worldBounds.getTop());
    }
}
