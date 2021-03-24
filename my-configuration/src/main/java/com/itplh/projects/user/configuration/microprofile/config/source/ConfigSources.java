package com.itplh.projects.user.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author: tanpenggood
 * @date: 2021-03-19 00:49
 */
public class ConfigSources implements Iterable<ConfigSource> {

    private List<ConfigSource> configSources = new LinkedList<>();
    /**
     * 是否已添加默认的ConfigSources
     */
    private boolean addedDefaultConfigSources;
    /**
     * 是否已添加SPI中的ConfigSources
     */
    private boolean addedDiscoveredConfigSources;

    private ClassLoader classLoader;

    public ConfigSources(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 添加默认的实现类
     */
    public void addDefaultConfigSources() {
        if (this.addedDefaultConfigSources) {
            return;
        }
        addConfigSources(JavaSystemPropertiesConfigSource.class,
                OperationSystemEnvironmentVariablesConfigSource.class,
                DefaultResourceConfigSource.class);
        this.addedDefaultConfigSources = true;
    }

    /**
     * 添加SPI配置的{@link ConfigSource}的实现类
     */
    public void addDiscoveredSources() {
        if (this.addedDiscoveredConfigSources) {
            return;
        }
        addConfigSources(ServiceLoader.load(ConfigSource.class, this.getClassLoader()));
        this.addedDiscoveredConfigSources = true;
    }

    /**
     * {@link ConfigSource} 优先级比较器
     *
     * @return
     */
    private Comparator<ConfigSource> getDefaultComparator() {
        return (s1, s2) -> Integer.compare(s2.getOrdinal(), s1.getOrdinal());
    }

    /**
     * 添加ConfigSources
     *
     * @param configSources
     */
    public void addConfigSources(Iterable<ConfigSource> configSources) {
        configSources.forEach(this.configSources::add);
        this.configSources.sort(getDefaultComparator());
    }

    /**
     * @param configSources
     * @see ConfigBuilder#withSources(org.eclipse.microprofile.config.spi.ConfigSource...)
     */
    public void addConfigSources(ConfigSource... configSources) {
        addConfigSources(Arrays.asList(configSources));
    }

    public void addConfigSources(Class<? extends ConfigSource>... configSourceClasses) {
        addConfigSources(Arrays.stream(configSourceClasses)
                .map(this::newInstance)
                .toArray(ConfigSource[]::new));
    }

    private ConfigSource newInstance(Class<? extends ConfigSource> configSourceClass) {
        try {
            return configSourceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Iterator<ConfigSource> iterator() {
        return configSources.iterator();
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public boolean isAddedDefaultConfigSources() {
        return addedDefaultConfigSources;
    }

    public boolean isAddedDiscoveredConfigSources() {
        return addedDiscoveredConfigSources;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
