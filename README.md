## 作业导航

第十五周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week15.md

第十四周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week14.md

第十三周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week13.md

第十二周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week12.md

第十一周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week11.md

第十周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week10.md

第九周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week9.md

第八周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week8.md

第七周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week7.md

第六周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week6.md

第五周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week5.md

第四周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week4.md

第三周：https://gitee.com/tanpenggood/geek-training-camp/blob/master/docs/README-week3.md

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
    
    `service.sh`脚本
    ```
    cd geek-training-camp
    
    # 项目clean
    ./service.sh clean
    
    # 项目package
    ./service.sh package
    
    # 以war启动
    ./service.sh war
    
    # 以jar启动
    ./service.sh jar
    ```

2. 启动时tomcat会输出 all access path.
