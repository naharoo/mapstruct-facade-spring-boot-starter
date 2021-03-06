package com.naharoo.commons.mapstruct;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

abstract class AbstractMappingTest {

    protected static final EasyRandom RANDOM = new EasyRandom();

    protected AnnotationConfigApplicationContext context;
    protected MappingFacade mappingFacade;

    @BeforeEach
    void initializeApplicationContext() {
        MappingsRegistry.clear();
        this.context = new AnnotationConfigApplicationContext(
                MapstructMapperFacadeAutoConfiguration.class,
                this.getClass()
        );
        this.mappingFacade = context.getBean(MappingFacade.class);
    }

    @AfterEach
    void closeApplicationContext() {
        context.close();
        MappingsRegistry.clear();
    }
}
