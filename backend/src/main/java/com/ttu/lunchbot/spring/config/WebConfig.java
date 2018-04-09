package com.ttu.lunchbot.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // All endpoints accessible by localhost:9000
        registry.addMapping("/**").allowedOrigins("http://localhost:9000");
    }
}
