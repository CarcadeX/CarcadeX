package me.redtea.carcadex.repo.decorator.impl;

import me.redtea.carcadex.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.repo.impl.CacheRepo;

public class EmptyMutableDecorator<K, V> extends CacheRepoDecorator<K, V> {
    public EmptyMutableDecorator() {
        super(null);
    }

    public void setRepo(CacheRepo<K, V> repo) {
        this.repo = repo;
    }

}
