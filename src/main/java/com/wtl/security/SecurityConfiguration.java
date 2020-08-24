package com.wtl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties properties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .authorizeRequests()
//                .antMatchers("/static/**", "/webjars/**", "/public/**", "/login", "/favicon.ico")
//                .permitAll() // 允许匿名访问的地址
//                .and() // 使用and()方法相当于XML标签的关闭，这样允许我们继续配置父类节点。
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated() // 其他地址都需要进行认证
//                .and()
                .formLogin()
                .and()
                .authorizeRequests() // 默认的登陆成功后的跳转地址
                .anyRequest()
                .authenticated()
        ;
    }
}
