package com.itplh.projects.user.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:47
 */
public class JavaConfig implements Config {

    /**
     * 内部可变的集合，不要直接暴露在外面
     */
    private List<ConfigSource> configSources = new LinkedList<>();

    private Map<Type, Converter> converterMapping = new HashMap<>();

    public JavaConfig() {
        // SPI加载所有ConfigSource实现类
        ClassLoader classLoader = this.getClass().getClassLoader();
        ServiceLoader.load(ConfigSource.class, classLoader).forEach(configSources::add);
        // 排序
        configSources.sort((c1, c2) -> Integer.compare(c2.getOrdinal(), c1.getOrdinal()));

        // SPI加载所有ConfigSource实现类，并保存到converterMapping
        for (Converter converter : ServiceLoader.load(Converter.class)) {
            Arrays.stream(converter.getClass().getGenericInterfaces())
                    .flatMap(type -> Stream.of(((ParameterizedType) type).getActualTypeArguments()))
                    .forEach(type -> converterMapping.put(type, converter));
        }
    }

    protected String getPropertyValue(String propertyName) {
        String propertyValue = null;
        for (ConfigSource configSource : this.configSources) {
            propertyValue = configSource.getValue(propertyName);
            if (propertyValue != null) {
                break;
            }
        }
        return propertyValue;
    }

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        String propertyValue = getPropertyValue(propertyName);
        // String 转换成目标类型
        return getConverter(propertyType).get().convert(propertyValue);
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        return Optional.ofNullable(getValue(propertyName, propertyType));
    }

    @Override
    public Iterable<String> getPropertyNames() {
        Set<String> propertyNames = new HashSet<>();
        for (ConfigSource configSource : this.configSources) {
            propertyNames.addAll(configSource.getPropertyNames());
        }
        return propertyNames;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        // 内部可变的集合，不要直接暴露在外面
        return Collections.unmodifiableCollection(this.configSources);
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        return Optional.of(converterMapping.get(forType));
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }
}
