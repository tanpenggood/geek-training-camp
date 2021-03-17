package com.itplh.projects.user.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:54
 */
public class JavaSystemPropertiesConfigSource implements ConfigSource {

    /**
     * Java 系统属性最好通过本地变量保存，使用 Map 保存，尽可能运行期不去调整
     * -Dapplication.name=user-web
     */
    private Map<String, String> properties;

    public JavaSystemPropertiesConfigSource() {
        // 返回的Properties对象是继承自Hashtable，这是一个有synchronized的Map，在并发读取配置时会出现性能问题
        Map systemProperties = System.getProperties();
        // 将配置放在一个无锁的容器，并发性更好
        this.properties = new HashMap<>(systemProperties);
    }

    @Override
    public Set<String> getPropertyNames() {
        return this.properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return this.properties.get(propertyName);
    }

    @Override
    public String getName() {
        return "Java System Properties";
    }
}
