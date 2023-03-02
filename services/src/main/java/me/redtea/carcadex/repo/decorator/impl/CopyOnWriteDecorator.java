package me.redtea.carcadex.repo.decorator.impl;

import me.redtea.carcadex.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.repo.impl.CacheRepo;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;


public class CopyOnWriteDecorator<K, V> extends CacheRepoDecorator<K, V> {
    private final CacheRepo<K, V> repo;

    public CopyOnWriteDecorator(CacheRepo<K, V> repo) {
        super(repo);
        this.repo = repo;
    }

    final transient ReentrantLock lock = new ReentrantLock();

    @Override
    public void loadToCache(K k) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            super.loadToCache(k);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void removeFromCache(K k) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            super.removeFromCache(k);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Collection<V> all() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return super.all();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Optional<V> get(K key) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }
    @Override
    public void saveAll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            super.saveAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V update(K key, V value) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            V oldValue = repo.get(key).orElse(null);
            if (oldValue != null && !oldValue.equals(value)) {
                repo.update(key, value);
            }
            return value;
        } finally {
            lock.unlock();
        }
    }

    public V remove(K key) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            V oldValue = repo.get(key).orElse(null);
            if (oldValue != null) {
                repo.remove(key);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }


}
