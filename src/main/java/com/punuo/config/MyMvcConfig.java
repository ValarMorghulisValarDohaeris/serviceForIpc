package com.punuo.config;

import com.punuo.interceptor.LoginInterceptor;
import com.punuo.interceptor.RoleInteceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lenovo on 2018/4/9.
 * @version 1.0
 */
//SpringMVC的配置类
@Configuration
public class MyMvcConfig extends WebMvcConfigurationSupport {
    //配置所写的拦截器
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/notLogin1","/notLogin2","/","/login1");

        registry.addInterceptor(new RoleInteceptor()).addPathPatterns("/addUser1","/addUser2");
    }
}
