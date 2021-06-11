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
> 
> 理解 Spring Cloud Bus 架构
> 
> 理解 Spring Cloud Stream

1. 实现源码

    ```
   my-spring-cloud-projects
        |- my-redis-bus
            |- src
                |- main
                    |- java
                        ｜- com.itplh.myredisbus.core.IConstant
                        ｜- com.itplh.myredisbus.core.RedisBusBridge                         BusBridge实现类
                        ｜- com.itplh.myredisbus.core.RedisBusBridgeSendAutoConfiguration    配置类-配置BusBridge#send到Redis的能力
                        ｜- com.itplh.myredisbus.core.RedisBusSubscribeReceiver              Redis Bus 消息接收器-处理消息并发送AckRemoteApplicationEvent
                        ｜- com.itplh.myredisbus.core.RemoteApplicationEventRedisSerializer  RedisValue序列化器-对RemoteApplicationEvent进行序列化/反序列化
                    |- resources
                        |- META-INF
                            ｜- spring.properties
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
        - `curl -X POST 'http://localhost:10000/actuator/busenv?name=demoData&value=888' -H 'content-type: application/json'`
        - Provider 服务日志输出
        
            ```
            2021-06-11 14:03:40.647  INFO 73002 --- [io-10000-exec-3] o.s.c.b.event.EnvironmentChangeListener  : Received remote environment change request. Keys/values to update {demoData=888}
            2021-06-11 14:03:40.906  INFO 73002 --- [io-10000-exec-3] c.itplh.myredisbus.core.RedisBusBridge   : send [EnvironmentChangeRemoteApplicationEvent@344e042a id = 'f2e248f7-189e-47a5-b35c-29917c760a98', originService = 'spring-cloud-service-provider:10000:${cachedrandom.${vcap.application.name:${spring.application.name:application}}.value}', destinationService = '**', values = map['demoData' -> '888']]
            ```
            
        - Consumer 服务日志输出
        
           ```
           2021-06-11 14:03:40.926  INFO 73135 --- [enerContainer-4] c.i.m.core.RedisBusSubscribeReceiver     : onMessage: {"type":"EnvironmentChangeRemoteApplicationEvent","timestamp":1623391420646,"originService":"spring-cloud-service-provider:10000:${cachedrandom.${vcap.application.name:${spring.application.name:application}}.value}","destinationService":"**","id":"f2e248f7-189e-47a5-b35c-29917c760a98","values":{"demoData":"888"}}
           2021-06-11 14:03:40.928  INFO 73135 --- [enerContainer-4] o.s.c.b.event.EnvironmentChangeListener  : Received remote environment change request. Keys/values to update {demoData=888}
           2021-06-11 14:03:41.017  INFO 73135 --- [enerContainer-4] c.itplh.myredisbus.core.RedisBusBridge   : send [AckRemoteApplicationEvent@57632686 id = 'a7506785-4964-45d2-b89f-8ac368a4a1f8', originService = 'spring-cloud-service-consumer:20000:${cachedrandom.${vcap.application.name:${spring.application.name:application}}.value}', destinationService = '**']
           2021-06-11 14:03:41.026  INFO 73135 --- [enerContainer-4] c.i.s.c.s.consumer.RedisBusController    : onAckEvent [AckRemoteApplicationEvent@57632686 id = 'a7506785-4964-45d2-b89f-8ac368a4a1f8', originService = 'spring-cloud-service-consumer:20000:${cachedrandom.${vcap.application.name:${spring.application.name:application}}.value}', destinationService = '**']
           2021-06-11 14:03:41.026  INFO 73135 --- [enerContainer-5] c.i.m.core.RedisBusSubscribeReceiver     : onMessage: {"type":"AckRemoteApplicationEvent","timestamp":1623391421016,"originService":"spring-cloud-service-consumer:20000:${cachedrandom.${vcap.application.name:${spring.application.name:application}}.value}","destinationService":"**","id":"a7506785-4964-45d2-b89f-8ac368a4a1f8","ackId":"f2e248f7-189e-47a5-b35c-29917c760a98","ackDestinationService":"**","event":"org.springframework.cloud.bus.event.EnvironmentChangeRemoteApplicationEvent"}
           ```
           
    6. 再次访问 Provider 服务的接口
        - 访问接口 http://localhost:10000/bus/redis/env?key=demoData
        - 返回值为 `888`
        
    7. 再次访问 Consumer 服务的接口
        - 访问接口 http://localhost:20000/bus/redis/env?key=demoData
        - 返回值为 `888`
        
    8. `Provider服务`与`Consumer服务`的接口返回值均变为`888`，说明本地配置与远程配置均更新成功。
`
## Usage



