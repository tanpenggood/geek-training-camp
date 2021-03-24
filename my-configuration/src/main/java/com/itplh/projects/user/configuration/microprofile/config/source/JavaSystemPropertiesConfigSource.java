package com.itplh.projects.user.configuration.microprofile.config.source;

import java.util.Map;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:54
 */
public class JavaSystemPropertiesConfigSource extends MapBasedConfigSource {

    public JavaSystemPropertiesConfigSource() {
        super("Java System Properties", 200);
    }

    /**
     * Java 系统属性最好通过本地变量保存，使用 Map 保存，尽可能运行期不去调整
     * -Dapplication.name=user-web
     *
     * @param configData {@link System#getProperties()}
     * @throws Throwable
     */
    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        configData.putAll(System.getProperties());
    }

}
