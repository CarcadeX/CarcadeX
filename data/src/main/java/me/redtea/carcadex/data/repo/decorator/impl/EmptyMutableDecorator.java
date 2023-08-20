package me.redtea.carcadex.data.repo.decorator.impl;

import me.redtea.carcadex.data.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.data.repo.impl.CacheRepo;

public class EmptyMutableDecorator<K, V> extends CacheRepoDecorator<K, V> {
    public EmptyMutableDecorator() {
        super(null);
    }

    public void setRepo(CacheRepo<K, V> repo) {
        this.repo = repo;
    }

}
