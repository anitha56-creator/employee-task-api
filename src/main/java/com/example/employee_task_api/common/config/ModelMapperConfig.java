package com.example.employee_task_api.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * ModelMapperConfig that implements the configuration for ModelMapper class.
 */
@Configuration
public class ModelMapperConfig {
    /**
     * Builds the primary modelMapper bean into DependencyInjection container.
     */
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true);
        return modelMapper;
    }
}