/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.itplh.cache.configuration;

import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;

/**
 * Cache {@link Configuration} utilities class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0
 */
public abstract class ConfigurationUtils {

    /**
     * As an instance of {@link MutableConfiguration}
     *
     * @param configuration {@link Configuration}
     * @param <K>           the type of key
     * @param <V>           the type of value
     * @return non-null
     * @see MutableConfiguration
     */
    public static <K, V> MutableConfiguration<K, V> mutableConfiguration(Configuration<K, V> configuration) {
        MutableConfiguration mutableConfiguration = null;
        if (configuration instanceof MutableConfiguration) {
            mutableConfiguration = (MutableConfiguration) configuration;
        } else if (configuration instanceof CompleteConfiguration) {
            CompleteConfiguration config = (CompleteConfiguration) configuration;
            mutableConfiguration = new MutableConfiguration<>(config);
        } else {
            mutableConfiguration = new MutableConfiguration<K, V>()
                    .setTypes(configuration.getKeyType(), configuration.getValueType())
                    .setStoreByValue(configuration.isStoreByValue());
        }
        return mutableConfiguration;
    }

    /**
     * As an immutable instance of {@link CompleteConfiguration}
     *
     * @param configuration {@link Configuration}
     * @param <K>           the type of key
     * @param <V>           the type of value
     * @return non-null
     * @see ImmutableCompleteConfiguration
     */
    public static <K, V> CompleteConfiguration<K, V> immutableConfiguration(Configuration<K, V> configuration) {
        return new ImmutableCompleteConfiguration(configuration);
    }

    public static <K, V> CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration(CacheEntryListener<? super K, ? super V> listener) {
        return cacheEntryListenerConfiguration(listener, null);
    }

    public static <K, V> CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration(CacheEntryListener<? super K, ? super V> listener,
                                                                                               CacheEntryEventFilter<? super K, ? super V> filter) {
        return cacheEntryListenerConfiguration(listener, filter, true);
    }

    public static <K, V> CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration(CacheEntryListener<? super K, ? super V> listener,
                                                                                               CacheEntryEventFilter<? super K, ? super V> filter,
                                                                                               boolean isOldValueRequired) {
        return cacheEntryListenerConfiguration(listener, filter, isOldValueRequired, true);
    }

    public static <K, V> CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration(CacheEntryListener<? super K, ? super V> listener,
                                                                                               CacheEntryEventFilter<? super K, ? super V> filter,
                                                                                               boolean isOldValueRequired,
                                                                                               boolean isSynchronous) {
        return new MutableCacheEntryListenerConfiguration<>(() -> listener, () -> filter, isOldValueRequired, isSynchronous);
    }
}
