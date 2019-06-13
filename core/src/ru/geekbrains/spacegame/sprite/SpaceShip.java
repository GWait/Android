package ru.geekbrains.spacegame.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.pool.BulletPool;
import ru.geekbrains.spacegame.pool.ExplosionPool;

public class SpaceShip extends Ship {

    private static int INVALID_POINTER = -1;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public SpaceShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1,2,2);
        speed = new Vector2();
        v0 = new Vector2(0.5f, 0);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        bulletSpeed = new Vector2(0, 0.5f);
        reloadInterval = 0.2f;
        this.bulletHight = 0.01f;
        this.damage = 1;
        this.hp = 100;
        this.bulletSound = bulletSound;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            fire();
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public boolean touchUp(Vector2 selectedPos, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 selectedPos, int pointer) {
        if (selectedPos.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    private void moveRight() {
        speed.set(v0);
    }

    private void moveLeft() {
        speed.set(v0).rotate(180);
    }

    private void stop() {
        speed.setZero();
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft() ||
                bullet.getLeft() > getRight() ||
                bullet.getBottom() > pos.y    ||
                bullet.getTop() < getBottom()
        );
    }

    public int getHp() {
        return this.hp;
    }

    public void heal(int hp) {
        this.hp = this.hp + hp > 100 ? 100 : this.hp + hp;
    }
}
