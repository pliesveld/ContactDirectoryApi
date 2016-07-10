package com.smhumayun.articles.angularspringdata.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Humayun on 5/23/2016.
 */
@SpringBootApplication
@EnableSpringDataWebSupport
public class ApiServer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "PUT", "POST", "DELETE", "HEAD", "TRACE", "OPTIONS", "PATCH")
                        .allowedHeaders("Cache-Control", "Pragma", "Origin", "Authorization", "Content-Type"
                                , "X-Requested-With", "Host", "Auth", "Token", "Access-Token", "Access_Token"
                                , "AccessToken", "Code", "accept", "Access-Control-Request-Method"
                                , "Access-Control-Request-Headers")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiServer.class, args);
    }

}
