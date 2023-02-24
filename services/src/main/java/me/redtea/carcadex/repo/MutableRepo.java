package me.redtea.carcadex.repo;

import me.redtea.carcadex.reload.Reloadable;

public interface MutableRepo<K, V> extends Repo<K, V>, Reloadable {

    V update(V value);

    V remove(K key);

    void saveAll();
}
