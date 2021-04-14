package com.itplh.cache.redis;

import com.itplh.cache.AbstractCache;
import com.itplh.cache.ExpirableEntry;
import redis.clients.jedis.Jedis;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.util.Set;

public class JedisCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {

    private final Jedis jedis;

    public JedisCache(CacheManager cacheManager, String cacheName,
                      Configuration<K, V> configuration, Jedis jedis) {
        super(cacheManager, cacheName, configuration);
        this.jedis = jedis;
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        jedis.set(javaSerializer.serialize(entry.getKey()), javaSerializer.serialize(entry.getValue()));
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        return jedis.exists(javaSerializer.serialize(key));
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        V value = (V) javaSerializer.deserialize(jedis.get(javaSerializer.serialize(key)), null);
        return ExpirableEntry.of(key, value);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        ExpirableEntry<K, V> oldEntry = getEntry(key);
        jedis.del(javaSerializer.serialize(key));
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
        this.jedis.close();
    }

}
