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
            |- com.itplh.rest.demo.RestClientDemo#testGetRequest                 同步GET测试
            |- com.itplh.rest.demo.RestClientDemo#testAsyncGetRequest            异步GET测试
            |- com.itplh.rest.demo.RestClientDemo#testPostRequest                同步POST测试
            |- com.itplh.rest.demo.RestClientDemo#testAsyncPostRequest           异步POST测试
            |- com.itplh.rest.demo.RestClientDemo#testAsyncPostRequestConcurrent 异步POST并发测试
    ```

3. 实现源码

    ```
    my-rest-client
    |- src
        |- main
            |- java
                ｜- com.itplh.rest.client.AbstractInvocation
                ｜- com.itplh.rest.client.HttpPostInvocation    发送GET请求
                ｜- com.itplh.rest.client.HttpPostInvocation    发送POST请求
                ｜- com.itplh.rest.core.MediaTypeHeaderDelegate 媒体类型请求头委派类（用于对 MediaType 进行转换）
    ```

## 作业三

> (可选) 读一下 Servlet 3.0 关于 Servlet 异步
> - AsyncContext
