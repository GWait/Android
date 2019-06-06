package ru.geekbrains.spacegame.pool;

import com.badlogic.gdx.audio.Sound;
import ru.geekbrains.spacegame.base.SpritesPool;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.sprite.EnemySpaceShip;
import ru.geekbrains.spacegame.sprite.SpaceShip;

public class EnemyPool extends SpritesPool<EnemySpaceShip> {

    private Sound bulletSound;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;
    private SpaceShip spaceShip;


    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound, Rect worldBounds, SpaceShip spaceShip) {
        this.bulletSound = bulletSound;
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.spaceShip = spaceShip;
    }

    @Override
    protected EnemySpaceShip newObject() {
        return new EnemySpaceShip(bulletPool, explosionPool, bulletSound, worldBounds, spaceShip);
    }
}
