package me.redtea.carcadex.repo.decorator.impl;

import me.redtea.carcadex.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.repo.impl.CacheRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

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
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.onlyOnDebug = onlyOnDebug;
    }

    public LoggingDecorator(CacheRepo<K, V> repo) {
        super(repo);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.onlyOnDebug = false;
    }

    @Override
    public void init() {
        try {
            log("Start of repository initialization...");
            super.init();
        } catch (Throwable e) {
            logger.error("Error on initialization repository! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public void close() {
        try {
            log("Closing the repository...");
            super.close();
        } catch (Throwable e) {
            logger.error("Error on closing repository! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public V update(K key, V value) {
        try {
            log("Updating the repository...");
            log("Key: " + key + " Value: " + value);
            return repo.update(key, value);
        } catch (Throwable e) {
            logger.error("Error on updating repository! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public V remove(K key) {
        try {
            log("Removing from repository...");
            log("Key: " + key);
            return repo.remove(key);
        } catch (Throwable e) {
            logger.error("Error on removing from repository! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public void saveAll() {
        try {
            log("Saving to storage from repository...");
            repo.saveAll();
        } catch (Throwable e) {
            logger.error("Error on saving to storage from repository! " + e.getLocalizedMessage());
            throw e;
        }

    }

    @Override
    public Collection<V> all() {
        try {
            log("Getting all values from memory...");
            return repo.all();
        } catch (Throwable e) {
            logger.error("Error on getting all values from memory! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public Optional<V> get(K key) {
        try {
            log("Getting value from repository...");
            log("Key: " + key);
            return repo.get(key);
        } catch (Throwable e) {
            logger.error("Error on getting value from repository! " + e.getLocalizedMessage());
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
            logger.error("Error on load to cache value! " + e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public void removeFromCache(K k) {
        try {
            log("Remove from cache value...");
            repo.remove(k);
        } catch (Throwable e) {
            logger.error("Error on remove from cache value! " + e.getLocalizedMessage());
            throw e;
        }
    }

    private void log(String log) {
        if(onlyOnDebug) logger.debug(log);
        else logger.info(log);
    }

}
