package com.github.matek2305.pt.api.config;

import com.github.matek2305.pt.api.ApiModule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Configuration
@ComponentScan(basePackageClasses = ApiModule.class)
public class ApiConfiguration {
}
