# week7

作业提交链接： https://jinshuju.net/f/qizKPb
        

## 作业一

> 使用 Spring Boot 来实现一个整合 Gitee 或者 Github OAuth2 认证

1. 使用技术

    - spring-boot 2.1.3.RELEASE
    - freemarker
    - hutool-http 5.0.7

2. 实现源码

    ```
    my-oauth-demo
        |- src
            |- main
                |- java
                    ｜- com.itplh.oauth.OAuthApplication             启动类
                    ｜- com.itplh.oauth.controller.OAuthController   相关接口（登录/退出/OAuth登录）
                    ｜- com.itplh.oauth.filter.OAuthFilter           资源访问控制过滤器
            |- resources
                |- templates
                    |- login.ftl                                     登录页
                    |- index.ftl                                     用户首页
    ```

3. 测试

    1. 启动项目 `com.itplh.oauth.OAuthApplication`
    2. 访问 http://localhost:8080
    3. 点击`Gitee登录`按钮，进行 Gitee OAuth 授权登录，成功进入到 http://localhost:8080/resource/index
    4. 点击首页的`退出登录`按钮，并使用首页提供的`username`及`password`可实现账号密码登录
