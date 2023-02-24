package ru.otus.cachehw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(MyCache.class);

    private Map<K, V> cache;
    private List<HwListener<K, V>> listeners;

    public MyCache() {
        this.cache = new WeakHashMap<>();
        this.listeners = new ArrayList<>();
    }

//Надо реализовать эти методы

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        log.debug("cache size after put: {}", cache.size());
        listeners.forEach(listener -> listener.notify(key, value, "put"));
    }

    @Override
    public void remove(K key) {
        V remove = cache.remove(key);
        log.debug("cache size after remove: {}", cache.size());
        listeners.forEach(listener -> listener.notify(key, remove, "remove"));
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        log.debug("cache size after get: {}", cache.size());
        listeners.forEach(listener -> listener.notify(key, value, "get"));
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
