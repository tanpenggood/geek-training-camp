package com.itplh.projects.user.configuration.microprofile.config;

import com.itplh.web.context.ComponentContext;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 22:17
 */
public class DefaultConfigProviderResolver extends ConfigProviderResolver {
    @Override
    public Config getConfig() {
        return getConfig(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public Config getConfig(ClassLoader loader) {
        // 先从容器获取Config的实现类，再通过SPI获取
        ComponentContext componentContext = ComponentContext.getInstance();
        List<Config> configs = componentContext.getComponents(Config.class);
        if (!configs.isEmpty()) {
            return configs.get(0);
        }
        // SPI获取Config的实现类
        ClassLoader classLoader = loader;
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        ServiceLoader<Config> serviceLoader = ServiceLoader.load(Config.class, classLoader);
        Iterator<Config> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            // 获取 Config SPI 第一个实现
            return iterator.next();
        }
        throw new IllegalStateException("No Config implementation found!");
    }

    @Override
    public ConfigBuilder getBuilder() {
        return null;
    }

    @Override
    public void registerConfig(Config config, ClassLoader classLoader) {

    }

    @Override
    public void releaseConfig(Config config) {

    }
}
