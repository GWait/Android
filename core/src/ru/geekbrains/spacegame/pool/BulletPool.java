package ru.geekbrains.spacegame.pool;

import ru.geekbrains.spacegame.base.SpritesPool;
import ru.geekbrains.spacegame.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
