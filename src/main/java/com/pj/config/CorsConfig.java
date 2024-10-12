package com.pj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 适用于所有路径
                .allowedOrigins("*") // 允许所有域名
                .allowedMethods("*") // 允许所有HTTP方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(false) // 不允许发送凭证（如Cookies），因为使用 "*" 时不能启用
                .maxAge(3600); // 预检请求的缓存时间（秒）
    }
}