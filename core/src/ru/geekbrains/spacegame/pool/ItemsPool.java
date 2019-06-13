package ru.geekbrains.spacegame.pool;

import ru.geekbrains.spacegame.base.SpritesPool;
import ru.geekbrains.spacegame.sprite.FirstAidKit;

public class ItemsPool extends SpritesPool<FirstAidKit> {

    @Override
    protected FirstAidKit newObject() {
        return new FirstAidKit();
    }
}
