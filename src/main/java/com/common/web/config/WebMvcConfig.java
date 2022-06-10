package com.common.web.config;


import com.common.web.filter.RequestFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableAsync
public class WebMvcConfig implements WebMvcConfigurer {
    private final RequestFilter requestFilter;

    public WebMvcConfig(RequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"/static/**"}).addResourceLocations(new String[]{"classpath:/static/"});
    }

    public void addInterceptors(InterceptorRegistry registry) {
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf((c) -> {
            return c instanceof StringHttpMessageConverter;
        });
    }
}