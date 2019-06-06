package ru.geekbrains.spacegame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.base.BaseScreen;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.pool.BulletPool;
import ru.geekbrains.spacegame.pool.EnemyPool;
import ru.geekbrains.spacegame.pool.ExplosionPool;
import ru.geekbrains.spacegame.sprite.*;
import ru.geekbrains.spacegame.util.EnemyGenerator;

import java.util.List;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private enum State {PLAY, PAUSE, GAMEOVER}

    private Game game;

    private Texture bg;
    private Background background;
    private SpaceShip spaceShip;
    private TextureAtlas atlas;
    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyGenerator enemyGenerator;
    private Sound laserSound;
    private Sound explosionSound;
    private Sound bulletSound;
    private Music music;

    private GameOver gameOver;

    private State state;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        initSounds();
        initBackground();
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        spaceShip = new SpaceShip(atlas, bulletPool, explosionPool, laserSound);

        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, worldBounds, spaceShip);
        enemyGenerator = new EnemyGenerator(worldBounds, enemyPool, atlas);
        gameOver = new GameOver(atlas);
        state = State.PLAY;
        initStars();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyedActiveObjects();
        draw();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAY) {
            spaceShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAY) {
            return;
        }
        List<EnemySpaceShip> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (EnemySpaceShip enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDistance = enemy.getHalfWidth() + spaceShip.getHalfWidth();
            if (enemy.pos.dst(spaceShip.pos) < minDistance) {
                enemy.destroy();
                spaceShip.destroy();
                state = State.GAMEOVER;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != spaceShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == spaceShip || bullet.isDestroyed()) {
                continue;
            }
            if (spaceShip.isBulletCollision(bullet)) {
                spaceShip.damage(bullet.getDamage());
                bullet.destroy();
                if (spaceShip.isDestroyed()) {
                    state = State.GAMEOVER;
                }
            }
        }
    }

    private void freeAllDestroyedActiveObjects() {
        bulletPool.freeDestroyedActiveSprites();
        explosionPool.freeDestroyedActiveSprites();
        enemyPool.freeDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 50, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAY) {
            spaceShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAMEOVER) {
            gameOver.draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAY) {
            spaceShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAY) {
            spaceShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 selectedPos, int pointer) {
        if (state == State.PLAY) {
            spaceShip.touchDown(selectedPos, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 selectedPos, int pointer) {
        if (state == State.PLAY) {
            spaceShip.touchUp(selectedPos, pointer);
        }
        return false;
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        laserSound.dispose();
        explosionSound.dispose();
        bulletSound.dispose();
        enemyPool.dispose();
        music.dispose();
        super.dispose();
    }

    private void initSounds() {
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    private void initStars() {
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
    }

    private void initBackground() {
        bg = new Texture("background.jpg");
        background = new Background(new TextureRegion(bg));
    }

    @Override
    public void pause() {
        super.pause();
        if (state == State.GAMEOVER) {
            return;
        }
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        super.resume();
        if (state == State.GAMEOVER) {
            return;
        }
        state = State.PLAY;
        music.play();
    }
}
