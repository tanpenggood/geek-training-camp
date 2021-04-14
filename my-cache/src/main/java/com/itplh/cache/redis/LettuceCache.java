package com.itplh.cache.redis;

import com.itplh.cache.AbstractCache;
import com.itplh.cache.ExpirableEntry;
import io.lettuce.core.api.sync.RedisCommands;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.util.Set;

public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {

    private final RedisCommands syncCommands;

    public LettuceCache(CacheManager cacheManager, String cacheName,
                        Configuration<K, V> configuration, RedisCommands syncCommands) {
        super(cacheManager, cacheName, configuration);
        this.syncCommands = syncCommands;
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        syncCommands.set(javaSerializer.serialize(entry.getKey()), javaSerializer.serialize(entry.getValue()));
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        return syncCommands.exists(javaSerializer.serialize(key)) >= 1L;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        Object serializeValue = syncCommands.get(javaSerializer.serialize(key));
        V value = (V) javaSerializer.deserialize(serializeValue.toString(), null);
        return ExpirableEntry.of(key, value);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        ExpirableEntry<K, V> oldEntry = getEntry(key);
        syncCommands.del(javaSerializer.serialize(key));
        return oldEntry;
    }

    @Override
    protected void clearEntries() throws CacheException {
        // TODO
    }

    @Override
    protected Set<K> keySet() {
        // TODO
        return null;
    }

    @Override
    protected void doClose() {
        syncCommands.shutdown(true);
    }

}
