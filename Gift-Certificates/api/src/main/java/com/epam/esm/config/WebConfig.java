package com.epam.esm.config;

import com.epam.esm.controller.TagController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configure Spring MVC.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class WebConfig implements WebMvcConfigurer {

}
