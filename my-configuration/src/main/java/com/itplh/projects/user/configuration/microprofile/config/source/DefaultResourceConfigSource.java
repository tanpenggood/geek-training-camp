package com.itplh.projects.user.configuration.microprofile.config.source;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:54
 */
public class DefaultResourceConfigSource extends MapBasedConfigSource {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static final String configFileLocation = "META-INF/micro-profile-config.properties";

    public DefaultResourceConfigSource() {
        super("Default File Config", 100);
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(configFileLocation);
        if (resource == null) {
            logger.info("The default config file can't be found in the classpath : " + configFileLocation);
            return;
        }
        try (InputStream inputStream = resource.openStream()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            configData.putAll(properties);
        }
    }

}
