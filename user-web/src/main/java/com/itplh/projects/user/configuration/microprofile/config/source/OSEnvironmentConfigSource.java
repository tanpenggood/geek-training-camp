package com.itplh.projects.user.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:54
 */
public class OSEnvironmentConfigSource implements ConfigSource {

    private Map<String, String> properties;

    public OSEnvironmentConfigSource() throws BackingStoreException {
        Preferences rootPreferences = Preferences.systemRoot();
        Preferences userPreferences = Preferences.userRoot();
        String[] rootKeys = rootPreferences.keys();
        String[] userKeys = userPreferences.keys();
        int initialCapacity = (rootKeys.length + userKeys.length) >> 2;
        this.properties = new HashMap<>(initialCapacity);
        // 加载系统环境变量
        Arrays.stream(rootKeys).forEach(key -> this.properties.put(key, rootPreferences.get(key, null)));
        // 加载用户环境变量
        Arrays.stream(userKeys).forEach(key -> this.properties.put(key, userPreferences.get(key, null)));
    }

    @Override
    public Set<String> getPropertyNames() {
        return this.properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return this.properties.get(propertyName);
    }

    @Override
    public String getName() {
        return "OSEnvironmentConfigSource";
    }
}
