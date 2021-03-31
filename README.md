## 作业导航

第三周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week3.md

第四周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week4.md

第五周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week5.md

## start up

1. 启动项目

    ```
    cd geek-training-camp
    mvn clean package -U
    
    # java -jar启动
    java -jar user-web/target/user-web-v1-SNAPSHOT-war-exec.jar
    
    # debug启动
    java -Xdebug -Xrunjdwp:transport=dt_socket,suspend=y,server=y,address=5005 -jar user-web/target/user-web-v1-SNAPSHOT-war-exec.jar
    
    # mvn tomcat7:run启动
    mvn tomcat7:run
    
    # mvnDebug启动
    mvnDebug tomcat7:run
    
    # 打包再启动（跳过测试编译及打包）
    mvn clean package -U -Dmaven.test.skip=true tomcat7:run
    ```

2. 启动时tomcat会输出 all access path.
