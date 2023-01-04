package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //@EnableWebSecurity是开启SpringSecurity的默认行为
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //默认Spring Security不允许iframe嵌套显示，我们需要设置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许iframe嵌套显示
        http.headers().frameOptions().sameOrigin();
        //登录设置
        http.authorizeRequests()
            .antMatchers("/static/**","/login").permitAll()  //允许匿名用户访问的路径
            .anyRequest().authenticated()    // 其它页面全部需要验证
            .and()
            .formLogin()
            .loginPage("/login")    //用户未登录时，访问任何需要权限的资源都转跳到该路径，即登录页面，此时登陆成功后会继续跳转到第一次访问的资源页面（相当于被过滤了一下）
            .defaultSuccessUrl("/") //登录认证成功后默认转跳的路径，意思时admin登录后也跳转到/user
            .and()
            .logout()
            .logoutUrl("/logout")   //退出登陆的路径，指定spring security拦截的注销url,退出功能是security提供的
            .logoutSuccessUrl("/login")//用户退出后要被重定向的url
             .and()
            .csrf().disable();//关闭跨域请求伪造功能
        //添加自定义异常入口
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler());
    }

    /**
     * 必须指定加密方式，上下加密方式要一致
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}