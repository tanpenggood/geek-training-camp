package com.itplh.projects.user.configuration.microprofile.config;

import com.itplh.projects.user.configuration.microprofile.config.converter.Converters;
import com.itplh.projects.user.configuration.microprofile.config.source.ConfigSources;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

/**
 * {@link DefaultConfig} 构建器
 *
 * @author: tanpenggood
 * @date: 2021-03-19 00:44
 */
public class DefaultConfigBuilder implements ConfigBuilder {

    private final ConfigSources configSources;
    private final Converters converters;

    public DefaultConfigBuilder(ClassLoader classLoader) {
        this.configSources = new ConfigSources(classLoader);
        this.converters = new Converters(classLoader);
    }

    /**
     * 添加默认的实现类
     *
     * @return
     */
    @Override
    public ConfigBuilder addDefaultSources() {
        this.configSources.addDefaultConfigSources();
        return this;
    }

    /**
     * 添加SPI配置的{@link ConfigSource}的实现类
     *
     * @return
     */
    @Override
    public ConfigBuilder addDiscoveredSources() {
        this.configSources.addDiscoveredSources();
        return this;
    }

    /**
     * 添加SPI配置的{@link Converter}的实现类
     *
     * @return
     */
    @Override
    public ConfigBuilder addDiscoveredConverters() {
        this.converters.addDiscoveredConverters();
        return this;
    }

    @Override
    public ConfigBuilder forClassLoader(ClassLoader loader) {
        this.configSources.setClassLoader(loader);
        this.converters.setClassLoader(loader);
        return this;
    }

    @Override
    public ConfigBuilder withSources(ConfigSource... sources) {
        this.configSources.addConfigSources(sources);
        return this;
    }

    @Override
    public ConfigBuilder withConverters(Converter<?>... converters) {
        this.converters.addConverters(converters);
        return this;
    }

    @Override
    public <T> ConfigBuilder withConverter(Class<T> type, int priority, Converter<T> converter) {
        this.converters.addConverter(type, priority, converter);
        return this;
    }

    @Override
    public Config build() {
        return new DefaultConfig(this.configSources, this.converters);
    }
    
}
