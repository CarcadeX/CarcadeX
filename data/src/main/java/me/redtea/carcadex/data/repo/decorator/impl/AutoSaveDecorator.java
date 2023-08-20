package me.redtea.carcadex.data.repo.decorator.impl;

import me.redtea.carcadex.data.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.data.repo.impl.CacheRepo;

import java.util.Timer;
import java.util.TimerTask;

public class AutoSaveDecorator<K, V> extends CacheRepoDecorator<K, V> {
    public AutoSaveDecorator(CacheRepo<K, V> repo, long delay /*in milisec*/, long period /*in milisec*/) {
        super(repo);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                saveAll();
            }
        }, delay, period);
    }
}
