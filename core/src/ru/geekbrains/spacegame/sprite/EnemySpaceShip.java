package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.pool.BulletPool;
import ru.geekbrains.spacegame.pool.ExplosionPool;

public class EnemySpaceShip extends Ship {

    private enum State { DESCENT, FIGHT }

    private State state;

    private Vector2 descentSpeed = new Vector2(0, -0.15f);

    private SpaceShip spaceShip;

    private int killAward;


    public EnemySpaceShip(BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound, Rect worldBounds, SpaceShip spaceShip) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
        this.speed = new Vector2();
        this.v0 = new Vector2();
        this.bulletSpeed = new Vector2();
        this.reloadTimer = reloadInterval;
        this.spaceShip = spaceShip;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (state) {
            case DESCENT:
                if (getTop() <= worldBounds.getTop()) {
                    speed.set(v0);
                    state = State.FIGHT;
                }
                break;

            case FIGHT:
                if (getBottom() < worldBounds.getBottom()) {
                    destroy();
                    spaceShip.damage(damage);
                }
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    fire();
                }
        }
    }

    public void set(TextureRegion[] regions,
                    Vector2 v0,
                    TextureRegion bulletRegion,
                    float bulletHight,
                    float bulletVY,
                    int damage,
                    float reloadInterval,
                    float height, int hp, int killAward) {
        this.regions = regions;
        this.speed.set(descentSpeed);
        this.state = State.DESCENT;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHight = bulletHight;
        this.bulletSpeed.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        this.killAward = killAward;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft() ||
                bullet.getLeft() > getRight() ||
                bullet.getBottom() > getTop() ||
                bullet.getTop() < pos.y
                );
    }

    public int getKillAward() {
        return killAward;
    }
}
