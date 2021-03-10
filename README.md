## start up

1. 启动项目

    ```
    cd geek-training-camp
    mvn clean package -U
    java -jar user-web/target/user-web-v1-SNAPSHOT-war-exec.jar
    # debug启动
    java -Xdebug -Xrunjdwp:transport=dt_socket,suspend=y,server=y,address=5005 -jar user-web/target/user-web-v1-SNAPSHOT-war-exec.jar
    ```

2. 访问注册页 http://localhost:8080/user/register-page
