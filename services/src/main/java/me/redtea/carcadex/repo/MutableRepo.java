package me.redtea.carcadex.repo;

import me.redtea.carcadex.reload.Reloadable;
import me.redtea.carcadex.repo.Repo;
import org.jetbrains.annotations.NotNull;

public interface MutableRepo<K, V> extends Repo<K, V>, Reloadable {

    V update(@NotNull K key, @NotNull V value);

    V remove(@NotNull K key);

    void saveAll();
}
