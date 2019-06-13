package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.base.Sprite;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.pool.BulletPool;
import ru.geekbrains.spacegame.pool.ExplosionPool;

public abstract class Ship extends Sprite {

    protected BulletPool bulletPool;
    protected Vector2 bulletSpeed;
    protected float bulletHight;

    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;

    protected Vector2 speed;
    protected Vector2 v0;
    protected Rect worldBounds;

    protected int damage;
    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

    protected float damageAnimateInterval = 0.1f;
    protected float damageAnimateTimer = damageAnimateInterval;

    protected Sound bulletSound;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship() {
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void destroy() {
        super.destroy();
        this.hp = 0;
        explode();
    }

    protected void fire() {
        bulletSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletSpeed, bulletHight, worldBounds, damage);
    }

    private void explode() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void damage(int dmg) {
        hp -= dmg;
        if (hp <= 0) {
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }
}
