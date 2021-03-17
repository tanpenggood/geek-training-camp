## week3

### 需求一

>整合 https://jolokia.org/
>
>实现一个自定义 JMX MBean，通过 Jolokia 做 Servlet 代理

1. 启动项目

    ```
    cd geek-training-camp
    
    mvn clean package -U -Dmaven.test.skip=true tomcat7:run
    ```
    
2. 访问自定义 UserMBean

    [http://localhost:8080/user-web/jolokia/read/com.itplh.projects.user.management:type=User](http://localhost:8080/user-web/jolokia/read/com.itplh.projects.user.management:type=User)

3. 更改 User 的 email字段

    http://localhost:8080/user-web/my-jolokia/write-user-email

4. 查看 User 的 email字段变化

    [http://localhost:8080/user-web/jolokia/read/com.itplh.projects.user.management:type=User](http://localhost:8080/user-web/jolokia/read/com.itplh.projects.user.management:type=User)

### 需求二

> - 继续完成 Microprofile config API 中的实现
>
>   - 扩展 org.eclipse.microprofile.config.spi.ConfigSource 实现，包括 OS 环境变量，以及本地配置文件
>
>   - 扩展 org.eclipse.microprofile.config.spi.Converter 实现，提供 String 类型到简单类型
>
> - 通过 org.eclipse.microprofile.config.Config 读取当前应用名称
>
>   - 应用名称 property name = “application.name”

1. 扩展`org.eclipse.microprofile.config.spi.ConfigSource`实现

    ```java
    // Program 参数
    com.itplh.projects.user.configuration.microprofile.config.source.JavaSystemPropertiesConfigSource
    // 本地配置文件
    com.itplh.projects.user.configuration.microprofile.config.source.LocalFileConfigSource
    // OS 环境变量
    com.itplh.projects.user.configuration.microprofile.config.source.OSEnvironmentConfigSource
    // ServletContext配置(web.xml)
    com.itplh.projects.user.configuration.microprofile.config.source.ServletContextConfigSource
    // JNDI配置(context.xml)
    com.itplh.projects.user.configuration.microprofile.config.source.JndiConfigSource
    ```
    
    扩展`org.eclipse.microprofile.config.spi.Converter`实现
    
    ```java
    com.itplh.projects.user.configuration.microprofile.config.converter.StringToByteConverter
    com.itplh.projects.user.configuration.microprofile.config.converter.StringToShortConverter
    com.itplh.projects.user.configuration.microprofile.config.converter.StringToIntegerConverter
    com.itplh.projects.user.configuration.microprofile.config.converter.StringToLongConverter
    com.itplh.projects.user.configuration.microprofile.config.converter.StringToFloatConverter
    com.itplh.projects.user.configuration.microprofile.config.converter.StringToDoubleConverter
    com.itplh.projects.user.configuration.microprofile.config.converter.StringToBooleanConverter
    com.itplh.projects.user.configuration.microprofile.config.converter.StringToCharacterConverter
    com.itplh.projects.user.configuration.microprofile.config.converter.StringToStringConverter
    ```

2. 通过`org.eclipse.microprofile.config.Config`读取当前应用名称

    http://localhost:8080/user-web/config/application-name
