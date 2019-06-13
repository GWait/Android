package ru.geekbrains.spacegame.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.spacegame.math.Rect;
import ru.geekbrains.spacegame.math.Rnd;
import ru.geekbrains.spacegame.pool.EnemyPool;
import ru.geekbrains.spacegame.sprite.EnemySpaceShip;

public class EnemyGenerator {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_SPEEDY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DMG = 1;
    private static final float ENEMY_SMALL_RELOADINT = 3f;
    private static final int ENEMY_SMALL_HP = 1;
    private static final int ENEMY_SMALL_AWARD = 1;

    private static final float ENEMY_MID_HEIGHT = 0.1f;
    private static final float ENEMY_MID_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MID_BULLET_SPEEDY = -0.25f;
    private static final int ENEMY_MID_BULLET_DMG = 5;
    private static final float ENEMY_MID_RELOADINT = 4f;
    private static final int ENEMY_MID_HP = 5;
    private static final int ENEMY_MID_AWARD = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_SPEEDY = -0.3f;
    private static final int ENEMY_BIG_BULLET_DMG = 10;
    private static final float ENEMY_BIG_RELOADINT = 1f;
    private static final int ENEMY_BIG_HP = 10;
    private static final int ENEMY_BIG_AWARD = 10;

    private Rect worldBounds;

    private float generationInterval = 4f;
    private float generationTimer;

    private final TextureRegion[] enemySmallRegion;
    private final TextureRegion[] enemyMidRegion;
    private final TextureRegion[] enemyBigRegion;

    private final Vector2 enemySmallSpeed = new Vector2(0f, -0.2f);
    private final Vector2 enemyMidSpeed = new Vector2(0f, -0.03f);
    private final Vector2 enemyBigSpeed = new Vector2(0f, -0.005f);

    private TextureRegion bulletRegion;

    private EnemyPool enemyPool;

    private int level;

    public EnemyGenerator(Rect worldBounds, EnemyPool enemyPool, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy0");
        TextureRegion textureRegion1 = atlas.findRegion("enemy1");
        TextureRegion textureRegion2 = atlas.findRegion("enemy2");
        this.enemySmallRegion = Regions.split(textureRegion0, 1,2,2);
        this.enemyMidRegion = Regions.split(textureRegion1, 1,2,2);
        this.enemyBigRegion = Regions.split(textureRegion2, 1,2,2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta, int score) {
        level = score / 50 + 1;
        generationTimer += delta;
        if (generationTimer >= generationInterval) {
            generationTimer = 0f;
            EnemySpaceShip enemy = enemyPool.obtain();
            float type = (float) Math.random();

            if (type < 0.6f) {
                enemy.set(enemySmallRegion, enemySmallSpeed,
                        bulletRegion, ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_SPEEDY, ENEMY_SMALL_BULLET_DMG * level,
                        ENEMY_SMALL_RELOADINT, ENEMY_SMALL_HEIGHT, ENEMY_SMALL_HP, ENEMY_SMALL_AWARD);
            } else if (type < 0.85f) {
                enemy.set(enemyMidRegion, enemyMidSpeed,
                        bulletRegion, ENEMY_MID_BULLET_HEIGHT,
                        ENEMY_MID_BULLET_SPEEDY, ENEMY_MID_BULLET_DMG * level,
                        ENEMY_MID_RELOADINT, ENEMY_MID_HEIGHT, ENEMY_MID_HP, ENEMY_MID_AWARD);
            } else {
                enemy.set(enemyBigRegion, enemyBigSpeed,
                        bulletRegion, ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_SPEEDY, ENEMY_BIG_BULLET_DMG * level,
                        ENEMY_BIG_RELOADINT, ENEMY_BIG_HEIGHT, ENEMY_BIG_HP, ENEMY_BIG_AWARD);
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    public int getLevel() {
        return level;
    }
}
