## week5

## 作业一

> 修复本程序 org.geektimes.reactive.streams 包下

测试启动类

```
my-reactive-message
|- src
    |- test
        |- com.itplh.reactive.streams.DefaultPublisher#main
```

## 作业二

> 继续完善 my-rest-client POST 方法

1. 启动项目

   ```
   cd geek-training-camp
   
   mvn clean package -U -Dmaven.test.skip=true tomcat7:run
   ```
   
   GET接口 http://localhost:8080/user-web/hello/get
   
   POST接口 http://localhost:8080/user-web/hello/post

2. REST请求测试方法

    ```
    my-rest-client
    |- src
        |- test
            |- com.itplh.rest.demo.RestClientDemo#testGetRequest       测试同步GET请求
            |- com.itplh.rest.demo.RestClientDemo#testAsyncGetRequest  测试异步GET请求
            |- com.itplh.rest.demo.RestClientDemo#testPostRequest      测试同步POST请求
            |- com.itplh.rest.demo.RestClientDemo#testAsyncPostRequest 测试异步POST请求
    ```

## 作业三

> (可选) 读一下 Servlet 3.0 关于 Servlet 异步
> - AsyncContext
