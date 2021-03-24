## week4

启动项目
```
cd geek-training-camp

mvn clean package -U -Dmaven.test.skip=true tomcat7:run
```

### 需求一

完善模块 `my-dependency-injection`
- ✅ 脱离`web.xml`配置，实现`ComponentContext`自动初始化

### 需求二

完善模块 `my-configuration`

- ✅ 通过注入`com.itplh.projects.user.configuration.microprofile.config.DefaultConfigProviderResolver`获取 Config

- ✅ 通过配置读取当前应用名称

    http://localhost:8080/user-web/config/application-name

## 拓展

新增模块 my-initializer
- 功能1：通过`HandlesTypes`注解实现自定义初始化器接口 com.itplh.initializer.MyApplicationInitializer
- 功能2：自定义 Order 注解，实现控制初始化器的执行顺序
