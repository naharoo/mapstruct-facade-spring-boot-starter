package com.naharoo.commons.mapstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@PrivateApi
@Configuration
public class MapstructMapperFacadeAutoConfiguration {

    @PrivateApi
    @Bean
    public MappingsRegistrationBeanPostProcessor mappingsRegistrationBeanPostProcessor() {
        return new MappingsRegistrationBeanPostProcessor();
    }

    @PrivateApi
    @Bean
    public MappingFacade simpleMappingFacade() {
        return new SimpleMappingFacade();
    }
}
