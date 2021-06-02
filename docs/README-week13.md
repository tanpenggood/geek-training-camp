# week13

作业提交链接： https://jinshuju.net/f/QuL00Q
        
## 作业一

> 基于文件系统为 Spring Cloud 提供 PropertySourceLocator 实现
> 
> 配置文件命名规则 (META-INF/config/default.properties 或者 META-INF/config/default.yaml)

1. 实现源码

    ```
   my-spring-cloud-projects
        |- config-center-base-file-system
            |- src
                |- main
                    |- java
                        ｜- com.itplh.config_center.ConfigCenterBaseFileSystemApplication           SpringBoot启动类
                        ｜- com.itplh.config_center.runner.ReadConfigAtFixedRateRunner              周期读取配置 META-INF/config/default.properties
                        ｜- com.itplh.config_center.locator.DefaultPropertiesPropertySourceLocator  PropertySourceLocator实现类
                        ｜- com.itplh.config_center.locator.DefaultPropertiesPropertySource         EnumerablePropertySource子类
                        ｜- com.itplh.config_center.locator.DynamicResourceMessageSource
                    |- resources
                        |- META-INF
                            ｜- spring.factories
                            ｜- config
                                ｜- default.properties                                               指定配置文件
                        |- bootstrap.yml
                        |- application.yml
    ```

2. 测试
    1. 启动项目 `com.itplh.config_center.ConfigCenterBaseFileSystemApplication`
    2. 观察控制台打印，确定已读取到`META-INF/config/default.properties`中的配置
    3. 修改`META-INF/config/default.properties` 中的配置，控制台打印的配置动态更新
        - 多次反复调试，配置未动态更新
        - 监听不到`ENTRY_MODIFY`事件，`watchService.take()`一直处于`await`状态

