package com.itplh.projects.user.configuration.microprofile.config;

import com.itplh.projects.user.configuration.microprofile.config.converter.Converters;
import com.itplh.projects.user.configuration.microprofile.config.source.ConfigSources;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:47
 */
public class DefaultConfig implements Config {

    private final ConfigSources configSources;
    private final Converters converters;

    public DefaultConfig(ConfigSources configSources, Converters converters) {
        this.configSources = configSources;
        this.converters = converters;
    }

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        String propertyValue = getPropertyValue(propertyName);
        // String 转换成目标类型
        Converter<T> converter = doGetConverter(propertyType);
        return converter == null ? null : converter.convert(propertyValue);
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        return Optional.ofNullable(getValue(propertyName, propertyType));
    }

    @Override
    public Iterable<String> getPropertyNames() {
        Set<String> propertyNames = new LinkedHashSet<>();
        for (ConfigSource configSource : this.configSources) {
            propertyNames.addAll(configSource.getPropertyNames());
        }
        return propertyNames;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return this.configSources;
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        Converter<T> converter = doGetConverter(forType);
        return converter == null ? Optional.empty() : Optional.of(converter);
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
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

    protected <T> Converter<T> doGetConverter(Class<T> forType) {
        List<Converter> converters = this.converters.getConverters(forType);
        return converters.isEmpty() ? null : converters.get(0);
    }

}
