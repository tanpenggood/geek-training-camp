package com.itplh.projects.user.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 基于Map数据结构{@link ConfigSource}的实现
 *
 * @author: tanpenggood
 * @date: 2021-03-24 20:25
 */
public abstract class MapBasedConfigSource implements ConfigSource {

    private final String name;
    private final int ordinal;
    private final Map<String, String> source;

    public MapBasedConfigSource(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
        this.source = getProperties();
    }

    /**
     * 准备配置数据
     *
     * @param configData
     * @throws Throwable
     */
    protected abstract void prepareConfigData(Map configData) throws Throwable;

    @Override
    public Map<String, String> getProperties() {
        HashMap<String, String> configData = new HashMap<>();
        try {
            prepareConfigData(configData);
        } catch (Throwable e) {
            throw new IllegalStateException("准备配置数据发生错误", e);
        }
        return Collections.unmodifiableMap(configData);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getOrdinal() {
        return this.ordinal;
    }

    @Override
    public Set<String> getPropertyNames() {
        return this.source.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return this.source.get(propertyName);
    }

}
