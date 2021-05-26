# week12

作业提交链接： https://jinshuju.net/f/HxOBY5
        
## 作业一

> 将上次（week10） MyBatis@Enable 模块驱动，封装成 SpringBoot Starter 方式
> 
> 参考:MyBatis Spring Project 里面会有 Spring Boot 实现

1. 实现源码

    ```
    my-mybatis
        |- my-mybatis-spring-boot-starter
            |- src
                |- main
                    |- java
                        ｜- com.itplh.mybatis.EnableMyBatis                    EnableMyBatis注解
                        ｜- com.itplh.mybatis.MyBatisBeanDefinitionRegistrar   实现SqlSessionFactoryBean自动装配
                        ｜- com.itplh.mybatis.factory.MyObjectFactory          自定义ObjectFactory实现
    ```

2. 测试用例

    ```
    my-mybatis
        |- my-mybatis-spring-boot-starter-demo
            |- src
                |- test
                    |- com.itplh.mybatis.EnableMyBatisTest#testSelectById  测试根据id查询
                    |- com.itplh.mybatis.EnableMyBatisTest#testSelectAll   测试查询全部
                    |- com.itplh.mybatis.EnableMyBatisTest#testSelectPage  测试分页查询
    ```
