# week14

作业提交链接： https://jinshuju.net/f/ocTopp

## 作业一

> 利用 Redis 实现 Spring Cloud Bus 中的 BusBridge，避免强 依赖于 Spring Cloud Stream。
> 
> 难点: 处理消息订阅
> 
> 目的:
> 
> 回顾 Spring 事件
> 理解 Spring Cloud Bus 架构
> 理解 Spring Cloud Stream

1. 实现源码

    ```
   my-spring-cloud-projects
        |- my-redis-bus
            |- src
                |- main
                    |- java
                        ｜- com.itplh.myredisbus.core.IConstant
                        ｜- com.itplh.myredisbus.core.RedisBusBridge              BusBridge实现类
                        ｜- com.itplh.myredisbus.core.RedisMessageChannelBinder   Binder实现类
                        ｜- com.itplh.myredisbus.core.RedisPubSubListener         用于监听Redis pub/sub消息
                        ｜- com.itplh.myredisbus.core.DevRedisPubSubListener      仅用于junit单元测试
                    |- resources
                        |- META-INF
                            ｜- redis.properties
                |- test
                    |- java
                        ｜- com.itplh.myredisbus.RedisPubSubTest                  Jedis pub/sub 单元测试
    ```

2. 测试

    1. 本地启动`redis 3.0+`，端口 6379
    2. 启动注册中心`com.itplh.spring.cloud.netflix.eureka.EurekaServer`
    3. 启动 Provider 服务`com.itplh.spring.cloud.service.provider.ProviderServer`
        - 访问接口 http://localhost:10000/bus/redis/env?key=demoData
        - 返回值为 `999`
    4. 启动 Consumer 服务`com.itplh.spring.cloud.service.consumer.ConsumerServer`
        - 访问接口 http://localhost:20000/bus/redis/env?key=demoData
        - 返回值为 `未知`
    5. 请求 Provider 服务的 actuator 接口刷新配置（POST）
        - `curl -X POST 'http://localhost:10000/actuator/busenv?name=demoData&value=888' -H 'content-type: application/json'
    6. 再次访问 Provider 服务的接口
        - 访问接口 http://localhost:10000/bus/redis/env?key=demoData
        - 返回值为 `888`
    7. 再次访问 Consumer 服务的接口
        - 访问接口 http://localhost:20000/bus/redis/env?key=demoData
        - 返回值为 `888`
    8. `Provider服务`与`Consumer服务`的接口返回值均变为`888`，说明本地配置与远程配置均更新成功。
`
## Usage



