package com.naharoo.commons.mapstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapstructMapperFacadeAutoConfiguration {

    @Bean
    public MappingsRegistrationBeanPostProcessor mappingsRegistrationBeanPostProcessor() {
        return new MappingsRegistrationBeanPostProcessor();
    }

    @Bean
    public MappingFacade simpleMappingFacade() {
        return new SimpleMappingFacade();
    }
}
