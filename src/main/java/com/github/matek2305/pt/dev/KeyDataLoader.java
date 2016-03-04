package com.github.matek2305.pt.dev;

import com.github.matek2305.dataloader.DataLoader;
import com.github.matek2305.pt.domain.entity.BaseEntity;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
interface KeyDataLoader<T extends BaseEntity, K extends Enum<K>> extends DataLoader {

    T getDevEntity(K key);
}
