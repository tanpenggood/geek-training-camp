package com.itplh.spring.cloud.config.client.locator;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

public class DefaultPropertiesPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        return new DefaultPropertiesPropertySource("defaultProperties", new DynamicResourceMessageSource());
    }
}
