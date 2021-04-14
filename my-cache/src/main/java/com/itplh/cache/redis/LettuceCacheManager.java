package com.itplh.cache.redis;

import com.itplh.cache.AbstractCacheManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * {@link javax.cache.CacheManager} based on Lettuce
 */
public class LettuceCacheManager extends AbstractCacheManager {

    private final RedisClient redisClient;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        if (uri.toString().startsWith("lettuce-")) {
            uri = URI.create(uri.toString().substring(8));
        }
        this.redisClient = RedisClient.create(RedisURI.create(uri));
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> syncCommands = connect.sync();
        return new LettuceCache(this, cacheName, configuration, syncCommands);
    }

    @Override
    protected void doClose() {
        redisClient.shutdown();
    }
}
