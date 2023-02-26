package me.redtea.carcadex.repo;

import me.redtea.carcadex.reload.Reloadable;
import me.redtea.carcadex.repo.Repo;

public interface MutableRepo<K, V> extends Repo<K, V>, Reloadable {

    V update(K key, V value);

    V remove(K key);

    void saveAll();
}
