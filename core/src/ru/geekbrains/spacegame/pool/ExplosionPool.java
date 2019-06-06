package ru.geekbrains.spacegame.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.spacegame.base.SpritesPool;
import ru.geekbrains.spacegame.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureAtlas atlas;
    private Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, explosionSound);
    }
}
