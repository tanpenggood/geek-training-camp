package com.itplh.projects.user.configuration.microprofile.config.source;

import java.util.Map;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:54
 */
public class OperationSystemEnvironmentVariablesConfigSource extends MapBasedConfigSource {

    public OperationSystemEnvironmentVariablesConfigSource() {
        super("Operation System Environment Variables", 300);
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        configData.putAll(System.getenv());
    }

}
