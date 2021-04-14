# week6

作业提交链接： https://jinshuju.net/f/OJ1nlq

## 作业一

> 提供一套抽象 API 实现对象的序列化和反序列化

1. 实现源码

    ```
    my-serialize
        |- src
            |- main
                |- java
                    ｜- com.itplh.serialize.AbstractSerializer
                    ｜- com.itplh.serialize.JavaSerializer     Java 序列化/反序列化
                    ｜- com.itplh.serialize.JsonSerializer     Json 序列化/反序列化
                    ｜- com.itplh.serialize.Assert             工具类 - 断言
                    ｜- com.itplh.serialize.Converter          工具类 - 基础类型转换
                    ｜- com.itplh.serialize.SerializeException 异常类
    ```

2. 测试用例

    ```
    my-serialize
        |- src
            |- test
                |- com.itplh.serialize.JavaSerializerTest      单元测试 - Java序列化/反序列化
                |- com.itplh.serialize.JsonSerializerTest      单元测试 - Json序列化/反序列化
    ```

## 作业二

> 通过 Lettuce 实现一套 Redis CacheManager 以及 Cache

1. 实现源码

    ```
    my-cache
        |- src
            |- main
                |- java
                    ｜- com.itplh.cache.AbstractCache
                    ｜- com.itplh.cache.redis.LettuceCache        
                    ｜- com.itplh.cache.redis.LettuceCacheManager
    ```

2. 测试用例

    ```
    my-cache
        |- src
            |- test
                |- com.itplh.cache.CachingTest#testSampleLettuce
    ```
