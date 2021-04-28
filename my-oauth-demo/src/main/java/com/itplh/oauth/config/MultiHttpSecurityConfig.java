package com.itplh.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: tanpenggood
 * @date: 2021-04-28 21:27
 */
@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Bean
    public PasswordEncoder getPass() {
        return new BCryptPasswordEncoder();
    }

    @Order(0)
    @Configuration
    public static class LoginPageConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // 表单认证
            http.csrf().disable()
                    .formLogin()
                    .loginPage("/login-page")
                    // org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
                    .loginProcessingUrl("/login") // 当发现/login 时认为是登录，需 要执行 UserDetailsServiceImpl
                    .defaultSuccessUrl("/resource/user") // 登录成功后，跳转到指定请求（此处是 post 请求）
                    .failureUrl("/login-page?error"); // 登录失败
            // logout
            // org.springframework.security.web.authentication.logout.LogoutFilter
            http.logout().logoutUrl("/logout");
            // url 拦截
            http.authorizeRequests()
                    .antMatchers("/login-page", "/login").permitAll()
                    .antMatchers("/oauth/login", "/*/callback").permitAll()
                    .anyRequest().authenticated();//所有的请求都必须被认证。必须登录 后才能访问。
            System.out.println("LoginPageConfig：" + http);
        }
    }

    @Order(1)
    @Configuration
    public static class StaticResourceSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/static/**");
            System.out.println("StaticResourceSecurityConfig：" + web);
        }

    }

    @Order(2)
    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            System.out.println("WebSecurityConfig：" + http);
        }
    }

}
