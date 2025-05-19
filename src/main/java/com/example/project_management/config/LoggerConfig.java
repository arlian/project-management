package com.example.project_management.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LoggerConfig {

    /**
     * Create a prototype‐scoped Logger bean whose name is
     * the declaring class of the injection point.
     */
    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint ip) {
        Class<?> clazz = ip.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(clazz);
    }
}
