package me.redtea.carcadex.data.repo.decorator.impl;

import me.redtea.carcadex.data.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.data.repo.impl.CacheRepo;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

public class LoggingDecorator<K, V> extends CacheRepoDecorator<K, V> {
    private final Logger logger;

    private final boolean onlyOnDebug;

    public LoggingDecorator(CacheRepo<K, V> repo, Logger logger, boolean onlyOnDebug) {
        super(repo);
        this.logger = logger;
        this.onlyOnDebug = onlyOnDebug;
    }

    public LoggingDecorator(CacheRepo<K, V> repo, boolean onlyOnDebug) {
        super(repo);
        this.logger = Logger.getGlobal();
        this.onlyOnDebug = onlyOnDebug;
    }

    public LoggingDecorator(CacheRepo<K, V> repo) {
        super(repo);
        this.logger = Logger.getGlobal();
        this.onlyOnDebug = false;
    }


    @Override
    public void close() {
        try {
            log("Closing the repository...");
            super.close();
        } catch (Throwable e) {
            logger.severe("Error on closing repository! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public V set(@NotNull K key, @NotNull V value) {
        try {
            log("Updating the repository...");
            log("Key: " + key + " Value: " + value);
            return repo.set(key, value);
        } catch (Throwable e) {
            logger.severe("Error on updating repository! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public V remove(@NotNull K key) {
        try {
            log("Removing from repository...");
            log("Key: " + key);
            return repo.remove(key);
        } catch (Throwable e) {
            logger.severe("Error on removing from repository! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public void saveAll() {
        try {
            log("Saving to storage from repository...");
            repo.saveAll();
        } catch (Throwable e) {
            logger.severe("Error on saving to storage from repository! " + e.getLocalizedMessage());
            throw e;
        }

    }

    @Override
    public Collection<V> all() {
        try {
            log("Getting all values from memory...");
            return repo.all();
        } catch (Throwable e) {
            logger.severe("Error on getting all values from memory! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public Optional<V> get(@NotNull K key) {
        try {
            log("Getting value from repository...");
            log("Key: " + key);
            return repo.get(key);
        } catch (Throwable e) {
            logger.severe("Error on getting value from repository! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public void loadToCache(K k) {
        try {
            log("Load to cache value...");
            log("Key: " + k);
            repo.loadToCache(k);
        } catch (Throwable e) {
            logger.severe("Error on load to cache value! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public void removeFromCache(K k) {
        try {
            log("Remove from cache value...");
            repo.remove(k);
        } catch (Throwable e) {
            logger.severe("Error on remove from cache value! " + e.getLocalizedMessage());
            throw e;
        }
    }

    private void log(String log) {
        if(onlyOnDebug) logger.info(log);
        else logger.info(log);
    }

}
