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

