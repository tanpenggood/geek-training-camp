# week15

作业提交链接： https://jinshuju.net/f/nL407C

## 作业一

> 通过 GraalVM 将一个简单 Spring Boot 工程构建为 Native Image，要求:
> 
> 代码要自己手写 @Controller @RequestMapping("/helloworld")
> 
> 相关插件可以参考 Spring Native Samples
> 
> (可选) 理解 Hint 注解的使用

1. 源码

    ```
   my-native
        |- native-demo
            |- src
                |- main
                    |- java
                        ｜- com.itplh.mynative.NativeDemoApplication
                    |- resources
                        ｜- hello.txt
    ```

2. 测试（Mac OS）

    1. 下载 [native-demo-for-mac](https://gitee.com/tanpenggood/geek-training-camp/attach_files/740616/download/native-demo-for-mac)
    
    2. 启动`native-demo-for-mac`
    
    3. 访问 http://localhost:8080/helloworld
        - 响应数据 `Hello, Spring Native.`

## Build Native Image

1. 下载安装GraalVM

2. install native-image
    - `gu install native-image`

    - 或者下载离线安装包：https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-21.1.0/native-image-installable-svm-java11-darwin-amd64-21.1.0.jar
        ```
        gu -L install ./native-image-installable-svm-java11-darwin-amd64-21.1.0.jar
        ```

3. 构建Native Image

    ```
    cd ./geek-training-camp/my-native/native-demo
    
    mvn -Pnative clean package -Dmaven.test.skip=true
    ```
    
    控制台日志：
    ```
    [INFO] Scanning for projects...
    [INFO]
    [INFO] -------------------< com.itplh.projects:native-demo >-------------------
    [INFO] Building my-native-demo v1-SNAPSHOT
    [INFO] --------------------------------[ jar ]---------------------------------
    [INFO]
    [INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ native-demo ---
    [INFO] Deleting /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target
    [INFO]
    [INFO] --- flatten-maven-plugin:1.2.5:clean (flatten.clean) @ native-demo ---
    [INFO] Deleting /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/.flattened-pom.xml
    [INFO]
    [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ native-demo ---
    [INFO] Using 'UTF-8' encoding to copy filtered resources.
    [INFO] Copying 1 resource
    [INFO]
    [INFO] --- flatten-maven-plugin:1.2.5:flatten (flatten) @ native-demo ---
    [INFO] Generating flattened POM of project com.itplh.projects:native-demo:jar:v1-SNAPSHOT...
    [INFO]
    [INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ native-demo ---
    [INFO] Changes detected - recompiling the module!
    [INFO] Compiling 1 source file to /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/classes
    [INFO]
    [INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ native-demo ---
    [INFO] Not copying test resources
    [INFO]
    [INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ native-demo ---
    [INFO] Not compiling test sources
    [INFO]
    [INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ native-demo ---
    [INFO] Tests are skipped.
    [INFO]
    [INFO] --- spring-aot-maven-plugin:0.10.0:generate (generate) @ native-demo ---
    [INFO] Spring Native operating mode: native
    [WARNING] Failed verification check: this type was requested to be added to configuration but is not resolvable: org.springframework.data.jpa.repository.support.EntityManagerBeanDefinitionRegistrarPostProcessor it will be skipped
    [WARNING] Failed verification check: this type was requested to be added to configuration but is not resolvable: org.springframework.security.config.annotation.web.configuration.AutowiredWebSecurityConfigurersIgnoreParents it will be skipped
    [WARNING] Failed verification check: this type was requested to be added to configuration but is not resolvable: io.netty.channel.socket.nio.NioSocketChannel it will be skipped
    [WARNING] Failed verification check: this type was requested to be added to configuration but is not resolvable: org.springframework.messaging.handler.annotation.MessageMapping it will be skipped
    [WARNING] Failed verification check: this type was requested to be added to configuration but is not resolvable: org.springframework.transaction.annotation.Transactional it will be skipped
    [WARNING] Failed verification check: this type was requested to be added to configuration but is not resolvable: javax.transaction.Transactional it will be skipped
    [WARNING] Failed verification check: this type was requested to be added to configuration but is not resolvable: org.springframework.transaction.annotation.Propagation it will be skipped
    [WARNING] Failed verification check: this type was requested to be added to configuration but is not resolvable: org.springframework.web.reactive.socket.server.upgrade.TomcatRequestUpgradeStrategy it will be skipped
    [INFO] Changes detected - recompiling the module!
    [INFO] Compiling 14 source files to /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/classes
    [INFO] /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/generated-sources/spring-aot/src/main/java/org/springframework/aot/StaticSpringFactories.java: /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/generated-sources/spring-aot/src/main/java/org/springframework/aot/StaticSpringFactories.java使用或覆盖了已过时的 API。
    [INFO] /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/generated-sources/spring-aot/src/main/java/org/springframework/aot/StaticSpringFactories.java: 有关详细信息, 请使用 -Xlint:deprecation 重新编译。
    [INFO] /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/generated-sources/spring-aot/src/main/java/org/springframework/aot/StaticSpringFactories.java: /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/generated-sources/spring-aot/src/main/java/org/springframework/aot/StaticSpringFactories.java使用了未经检查或不安全的操作。
    [INFO] /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/generated-sources/spring-aot/src/main/java/org/springframework/aot/StaticSpringFactories.java: 有关详细信息, 请使用 -Xlint:unchecked 重新编译。
    [INFO] Using 'UTF-8' encoding to copy filtered resources.
    [INFO] Using 'UTF-8' encoding to copy filtered properties files.
    [INFO] Copying 6 resources
    [INFO]
    [INFO] --- maven-jar-plugin:3.2.0:jar (default-jar) @ native-demo ---
    [INFO] Building jar: /Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/native-demo-v1-SNAPSHOT.jar
    [INFO]
    [INFO] --- tomcat7-maven-plugin:2.1:exec-war-only (tomcat-run) @ native-demo ---
    [INFO]
    [INFO] --- native-maven-plugin:0.9.0:build (build-native) @ native-demo ---
    [INFO] ImageClasspath Entry: org.springframework.boot:spring-boot-starter-web:jar:2.4.5:compile (file:///Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter-web/2.4.5/spring-boot-starter-web-2.4.5.jar)
    [INFO] ImageClasspath Entry: org.springframework.boot:spring-boot-starter:jar:2.4.5:compile (file:///Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter/2.4.5/spring-boot-starter-2.4.5.jar)
    [INFO] ImageClasspath Entry: org.springframework.boot:spring-boot:jar:2.4.5:compile (file:///Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot/2.4.5/spring-boot-2.4.5.jar)
    [INFO] ImageClasspath Entry: org.springframework.boot:spring-boot-autoconfigure:jar:2.4.5:compile (file:///Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/2.4.5/spring-boot-autoconfigure-2.4.5.jar)
    [INFO] ImageClasspath Entry: org.springframework.boot:spring-boot-starter-logging:jar:2.4.5:compile (file:///Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter-logging/2.4.5/spring-boot-starter-logging-2.4.5.jar)
    [INFO] ImageClasspath Entry: ch.qos.logback:logback-classic:jar:1.2.3:compile (file:///Users/tanpeng/.m2/repository/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar)
    [INFO] ImageClasspath Entry: ch.qos.logback:logback-core:jar:1.2.3:compile (file:///Users/tanpeng/.m2/repository/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar)
    [INFO] ImageClasspath Entry: org.slf4j:slf4j-api:jar:1.7.30:compile (file:///Users/tanpeng/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar)
    [INFO] ImageClasspath Entry: org.apache.logging.log4j:log4j-to-slf4j:jar:2.13.3:compile (file:///Users/tanpeng/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.13.3/log4j-to-slf4j-2.13.3.jar)
    [INFO] ImageClasspath Entry: org.apache.logging.log4j:log4j-api:jar:2.13.3:compile (file:///Users/tanpeng/.m2/repository/org/apache/logging/log4j/log4j-api/2.13.3/log4j-api-2.13.3.jar)
    [INFO] ImageClasspath Entry: org.slf4j:jul-to-slf4j:jar:1.7.30:compile (file:///Users/tanpeng/.m2/repository/org/slf4j/jul-to-slf4j/1.7.30/jul-to-slf4j-1.7.30.jar)
    [INFO] ImageClasspath Entry: jakarta.annotation:jakarta.annotation-api:jar:1.3.5:compile (file:///Users/tanpeng/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar)
    [INFO] ImageClasspath Entry: org.springframework:spring-core:jar:5.3.6:compile (file:///Users/tanpeng/.m2/repository/org/springframework/spring-core/5.3.6/spring-core-5.3.6.jar)
    [INFO] ImageClasspath Entry: org.springframework:spring-jcl:jar:5.3.6:compile (file:///Users/tanpeng/.m2/repository/org/springframework/spring-jcl/5.3.6/spring-jcl-5.3.6.jar)
    [INFO] ImageClasspath Entry: org.yaml:snakeyaml:jar:1.27:compile (file:///Users/tanpeng/.m2/repository/org/yaml/snakeyaml/1.27/snakeyaml-1.27.jar)
    [INFO] ImageClasspath Entry: org.springframework.boot:spring-boot-starter-json:jar:2.4.5:compile (file:///Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter-json/2.4.5/spring-boot-starter-json-2.4.5.jar)
    [INFO] ImageClasspath Entry: com.fasterxml.jackson.core:jackson-databind:jar:2.10.1:compile (file:///Users/tanpeng/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.10.1/jackson-databind-2.10.1.jar)
    [INFO] ImageClasspath Entry: com.fasterxml.jackson.core:jackson-annotations:jar:2.10.1:compile (file:///Users/tanpeng/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.10.1/jackson-annotations-2.10.1.jar)
    [INFO] ImageClasspath Entry: com.fasterxml.jackson.core:jackson-core:jar:2.10.1:compile (file:///Users/tanpeng/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.10.1/jackson-core-2.10.1.jar)
    [INFO] ImageClasspath Entry: com.fasterxml.jackson.datatype:jackson-datatype-jdk8:jar:2.11.4:compile (file:///Users/tanpeng/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.11.4/jackson-datatype-jdk8-2.11.4.jar)
    [INFO] ImageClasspath Entry: com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.11.4:compile (file:///Users/tanpeng/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.11.4/jackson-datatype-jsr310-2.11.4.jar)
    [INFO] ImageClasspath Entry: com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.11.4:compile (file:///Users/tanpeng/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.11.4/jackson-module-parameter-names-2.11.4.jar)
    [INFO] ImageClasspath Entry: org.springframework.boot:spring-boot-starter-tomcat:jar:2.4.5:compile (file:///Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat/2.4.5/spring-boot-starter-tomcat-2.4.5.jar)
    [INFO] ImageClasspath Entry: org.apache.tomcat.embed:tomcat-embed-core:jar:9.0.45:compile (file:///Users/tanpeng/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/9.0.45/tomcat-embed-core-9.0.45.jar)
    [INFO] ImageClasspath Entry: org.glassfish:jakarta.el:jar:3.0.3:compile (file:///Users/tanpeng/.m2/repository/org/glassfish/jakarta.el/3.0.3/jakarta.el-3.0.3.jar)
    [INFO] ImageClasspath Entry: org.apache.tomcat.embed:tomcat-embed-websocket:jar:9.0.45:compile (file:///Users/tanpeng/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/9.0.45/tomcat-embed-websocket-9.0.45.jar)
    [INFO] ImageClasspath Entry: org.springframework:spring-web:jar:5.3.6:compile (file:///Users/tanpeng/.m2/repository/org/springframework/spring-web/5.3.6/spring-web-5.3.6.jar)
    [INFO] ImageClasspath Entry: org.springframework:spring-beans:jar:5.3.6:compile (file:///Users/tanpeng/.m2/repository/org/springframework/spring-beans/5.3.6/spring-beans-5.3.6.jar)
    [INFO] ImageClasspath Entry: org.springframework:spring-webmvc:jar:5.3.6:compile (file:///Users/tanpeng/.m2/repository/org/springframework/spring-webmvc/5.3.6/spring-webmvc-5.3.6.jar)
    [INFO] ImageClasspath Entry: org.springframework:spring-aop:jar:5.3.6:compile (file:///Users/tanpeng/.m2/repository/org/springframework/spring-aop/5.3.6/spring-aop-5.3.6.jar)
    [INFO] ImageClasspath Entry: org.springframework:spring-context:jar:5.3.6:compile (file:///Users/tanpeng/.m2/repository/org/springframework/spring-context/5.3.6/spring-context-5.3.6.jar)
    [INFO] ImageClasspath Entry: org.springframework:spring-expression:jar:5.3.6:compile (file:///Users/tanpeng/.m2/repository/org/springframework/spring-expression/5.3.6/spring-expression-5.3.6.jar)
    [INFO] ImageClasspath Entry: org.springframework.experimental:spring-native:jar:0.10.0:compile (file:///Users/tanpeng/.m2/repository/org/springframework/experimental/spring-native/0.10.0/spring-native-0.10.0.jar)
    [INFO] ImageClasspath Entry: com.itplh.projects:native-demo:jar:v1-SNAPSHOT (file:///Users/tanpeng/tp-file/%E5%B0%8F%E9%A9%AC%E5%93%A5%E8%AE%AD%E7%BB%83%E8%90%A5/geek-training-camp/my-native/native-demo/target/native-demo-v1-SNAPSHOT.jar)
    [WARNING] jar:file:///Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/native-demo-v1-SNAPSHOT.jar!/META-INF/native-image/org.springframework.aot/spring-aot/native-image.properties does not match recommended META-INF/native-image/${groupId}/${artifactId}/native-image.properties layout.
    [INFO] Executing: /Users/tanpeng/.jenv/versions/graalvm64-11.0.11/bin/native-image -cp /Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter-web/2.4.5/spring-boot-starter-web-2.4.5.jar:/Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter/2.4.5/spring-boot-starter-2.4.5.jar:/Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot/2.4.5/spring-boot-2.4.5.jar:/Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/2.4.5/spring-boot-autoconfigure-2.4.5.jar:/Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter-logging/2.4.5/spring-boot-starter-logging-2.4.5.jar:/Users/tanpeng/.m2/repository/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar:/Users/tanpeng/.m2/repository/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar:/Users/tanpeng/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar:/Users/tanpeng/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.13.3/log4j-to-slf4j-2.13.3.jar:/Users/tanpeng/.m2/repository/org/apache/logging/log4j/log4j-api/2.13.3/log4j-api-2.13.3.jar:/Users/tanpeng/.m2/repository/org/slf4j/jul-to-slf4j/1.7.30/jul-to-slf4j-1.7.30.jar:/Users/tanpeng/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/Users/tanpeng/.m2/repository/org/springframework/spring-core/5.3.6/spring-core-5.3.6.jar:/Users/tanpeng/.m2/repository/org/springframework/spring-jcl/5.3.6/spring-jcl-5.3.6.jar:/Users/tanpeng/.m2/repository/org/yaml/snakeyaml/1.27/snakeyaml-1.27.jar:/Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter-json/2.4.5/spring-boot-starter-json-2.4.5.jar:/Users/tanpeng/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.10.1/jackson-databind-2.10.1.jar:/Users/tanpeng/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.10.1/jackson-annotations-2.10.1.jar:/Users/tanpeng/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.10.1/jackson-core-2.10.1.jar:/Users/tanpeng/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.11.4/jackson-datatype-jdk8-2.11.4.jar:/Users/tanpeng/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.11.4/jackson-datatype-jsr310-2.11.4.jar:/Users/tanpeng/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.11.4/jackson-module-parameter-names-2.11.4.jar:/Users/tanpeng/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat/2.4.5/spring-boot-starter-tomcat-2.4.5.jar:/Users/tanpeng/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/9.0.45/tomcat-embed-core-9.0.45.jar:/Users/tanpeng/.m2/repository/org/glassfish/jakarta.el/3.0.3/jakarta.el-3.0.3.jar:/Users/tanpeng/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/9.0.45/tomcat-embed-websocket-9.0.45.jar:/Users/tanpeng/.m2/repository/org/springframework/spring-web/5.3.6/spring-web-5.3.6.jar:/Users/tanpeng/.m2/repository/org/springframework/spring-beans/5.3.6/spring-beans-5.3.6.jar:/Users/tanpeng/.m2/repository/org/springframework/spring-webmvc/5.3.6/spring-webmvc-5.3.6.jar:/Users/tanpeng/.m2/repository/org/springframework/spring-aop/5.3.6/spring-aop-5.3.6.jar:/Users/tanpeng/.m2/repository/org/springframework/spring-context/5.3.6/spring-context-5.3.6.jar:/Users/tanpeng/.m2/repository/org/springframework/spring-expression/5.3.6/spring-expression-5.3.6.jar:/Users/tanpeng/.m2/repository/org/springframework/experimental/spring-native/0.10.0/spring-native-0.10.0.jar:/Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/my-native/native-demo/target/native-demo-v1-SNAPSHOT.jar -H:Name=native-demo
    [native-demo:22726]    classlist:   3,546.36 ms,  0.96 GB
    [native-demo:22726]        (cap):   2,533.81 ms,  0.96 GB
    [native-demo:22726]        setup:   6,855.94 ms,  0.96 GB
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/json/bind/Jsonb.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: net/sf/ehcache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration. Reason: java.lang.NoClassDefFoundError: com/hazelcast/core/HazelcastInstance.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/validation/Validator.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/serializer/RedisSerializer.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration. Reason: java.lang.NoClassDefFoundError: com/google/gson/GsonBuilder.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/connection/RedisConnectionFactory.
    WARNING: Could not register reflection metadata for org.springframework.web.multipart.commons.CommonsMultipartResolver. Reason: java.lang.NoClassDefFoundError: org/apache/commons/fileupload/FileItemFactory.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/cache/caffeine/CaffeineCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/infinispan/manager/EmbeddedCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/couchbase/cache/CouchbaseCacheManager$CouchbaseCacheManagerBuilder.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: javax/cache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/json/bind/Jsonb.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: net/sf/ehcache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration. Reason: java.lang.NoClassDefFoundError: com/hazelcast/core/HazelcastInstance.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/validation/Validator.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/serializer/RedisSerializer.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration. Reason: java.lang.NoClassDefFoundError: com/google/gson/GsonBuilder.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/connection/RedisConnectionFactory.
    WARNING: Could not register reflection metadata for org.springframework.web.multipart.commons.CommonsMultipartResolver. Reason: java.lang.NoClassDefFoundError: org/apache/commons/fileupload/FileItemFactory.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/cache/caffeine/CaffeineCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/infinispan/manager/EmbeddedCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/couchbase/cache/CouchbaseCacheManager$CouchbaseCacheManagerBuilder.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: javax/cache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: net/sf/ehcache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration. Reason: java.lang.NoClassDefFoundError: com/hazelcast/core/HazelcastInstance.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/serializer/RedisSerializer.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration. Reason: java.lang.NoClassDefFoundError: com/google/gson/GsonBuilder.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/cache/caffeine/CaffeineCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/infinispan/manager/EmbeddedCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/couchbase/cache/CouchbaseCacheManager$CouchbaseCacheManagerBuilder.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/json/bind/Jsonb.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/validation/Validator.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/connection/RedisConnectionFactory.
    WARNING: Could not register reflection metadata for org.springframework.web.multipart.commons.CommonsMultipartResolver. Reason: java.lang.NoClassDefFoundError: org/apache/commons/fileupload/FileItemFactory.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: javax/cache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: net/sf/ehcache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration. Reason: java.lang.NoClassDefFoundError: com/hazelcast/core/HazelcastInstance.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/serializer/RedisSerializer.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration. Reason: java.lang.NoClassDefFoundError: com/google/gson/GsonBuilder.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/cache/caffeine/CaffeineCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/infinispan/manager/EmbeddedCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/couchbase/cache/CouchbaseCacheManager$CouchbaseCacheManagerBuilder.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/json/bind/Jsonb.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/validation/Validator.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/connection/RedisConnectionFactory.
    WARNING: Could not register reflection metadata for org.springframework.web.multipart.commons.CommonsMultipartResolver. Reason: java.lang.NoClassDefFoundError: org/apache/commons/fileupload/FileItemFactory.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: javax/cache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: net/sf/ehcache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration. Reason: java.lang.NoClassDefFoundError: com/hazelcast/core/HazelcastInstance.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/serializer/RedisSerializer.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration. Reason: java.lang.NoClassDefFoundError: com/google/gson/GsonBuilder.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/cache/caffeine/CaffeineCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/infinispan/manager/EmbeddedCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/couchbase/cache/CouchbaseCacheManager$CouchbaseCacheManagerBuilder.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/json/bind/Jsonb.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/validation/Validator.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/connection/RedisConnectionFactory.
    WARNING: Could not register reflection metadata for org.springframework.web.multipart.commons.CommonsMultipartResolver. Reason: java.lang.NoClassDefFoundError: org/apache/commons/fileupload/FileItemFactory.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: javax/cache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: net/sf/ehcache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration. Reason: java.lang.NoClassDefFoundError: com/hazelcast/core/HazelcastInstance.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/serializer/RedisSerializer.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration. Reason: java.lang.NoClassDefFoundError: com/google/gson/GsonBuilder.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/cache/caffeine/CaffeineCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/infinispan/manager/EmbeddedCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/couchbase/cache/CouchbaseCacheManager$CouchbaseCacheManagerBuilder.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/json/bind/Jsonb.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/validation/Validator.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/connection/RedisConnectionFactory.
    WARNING: Could not register reflection metadata for org.springframework.web.multipart.commons.CommonsMultipartResolver. Reason: java.lang.NoClassDefFoundError: org/apache/commons/fileupload/FileItemFactory.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: javax/cache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: net/sf/ehcache/CacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration. Reason: java.lang.NoClassDefFoundError: com/hazelcast/core/HazelcastInstance.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/serializer/RedisSerializer.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration. Reason: java.lang.NoClassDefFoundError: com/google/gson/GsonBuilder.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/cache/caffeine/CaffeineCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/infinispan/manager/EmbeddedCacheManager.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/couchbase/cache/CouchbaseCacheManager$CouchbaseCacheManagerBuilder.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/json/bind/Jsonb.
    WARNING: Could not register reflection metadata for org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator. Reason: java.lang.NoClassDefFoundError: org/aspectj/util/PartialOrder$PartialComparable.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration. Reason: java.lang.NoClassDefFoundError: javax/validation/Validator.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration. Reason: java.lang.NoClassDefFoundError: org/springframework/data/redis/connection/RedisConnectionFactory.
    WARNING: Could not register reflection metadata for org.springframework.web.multipart.commons.CommonsMultipartResolver. Reason: java.lang.NoClassDefFoundError: org/apache/commons/fileupload/FileItemFactory.
    WARNING: Could not register reflection metadata for org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration. Reason: java.lang.NoClassDefFoundError: javax/cache/CacheManager.
    [native-demo:21753]     (clinit):   3,380.70 ms,  3.06 GB
    [native-demo:21753]   (typeflow):  55,584.73 ms,  3.06 GB
    [native-demo:21753]    (objects):  75,516.49 ms,  3.06 GB
    [native-demo:21753]   (features):   7,062.38 ms,  3.06 GB
    [native-demo:21753]     analysis: 148,552.12 ms,  3.06 GB
    [native-demo:21753]     universe:   6,994.02 ms,  3.08 GB
    [native-demo:21753]      (parse):  24,035.26 ms,  3.59 GB
    [native-demo:21753]     (inline):  17,078.34 ms,  4.17 GB
    [native-demo:21753]    (compile):  83,127.69 ms,  4.39 GB
    [native-demo:21753]      compile: 132,093.53 ms,  4.39 GB
    [native-demo:21753]        image:  18,153.49 ms,  4.17 GB
    [native-demo:21753]        write:   2,997.93 ms,  4.17 GB
    # Printing build artifacts to: native-demo.build_artifacts.txt
    [native-demo:21753]      [total]: 320,307.21 ms,  4.17 GB
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  05:39 min
    [INFO] Finished at: 2021-06-17T00:53:59+08:00
    [INFO] ------------------------------------------------------------------------
    ```



