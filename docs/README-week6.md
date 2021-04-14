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
                    ｜- com.itplh.serialize.JsonSerializer#serialize    序列化
                    ｜- com.itplh.serialize.JsonSerializer#deserialize  反序列化
    ```

2. 测试用例

    ```
    my-serialize
        |- src
            |- test
                |- com.itplh.serialize.JsonSerializerTest
    ```

## 作业二

> 通过 Lettuce 实现一套 Redis CacheManager 以及 Cache
