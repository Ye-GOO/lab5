package com.dgut.lab5.config;


import com.dgut.lab5.Filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogFilter());
        registration.addUrlPatterns("/punch","/riskarea","/fever","/channel","/updateArea");
        registration.setName("LogFilter");
        registration.setOrder(1);
        return registration;
    }
}
