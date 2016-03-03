package me.laudukang.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 16:15
 * <p>Version: 1.0
 */
@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = {"me.laudukang.config", "me.laudukang.service"})
public class ApplicationConfig {
}
