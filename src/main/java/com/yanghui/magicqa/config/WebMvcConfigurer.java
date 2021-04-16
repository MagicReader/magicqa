package com.yanghui.magicqa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import com.yanghui.magicqa.interceptor.TokenInterceptor;


@Configuration  //适配器
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

    @Bean
    public TokenInterceptor getTokenInterceptor() {
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 拦截器按照顺序执行
         */
        registry.addInterceptor(getTokenInterceptor())
                .addPathPatterns("/**") //拦截所有请求
                .excludePathPatterns("/login","/register","/admin/login","/testAPI"); //对应的不拦截的请求
        super.addInterceptors(registry);
    }
}
