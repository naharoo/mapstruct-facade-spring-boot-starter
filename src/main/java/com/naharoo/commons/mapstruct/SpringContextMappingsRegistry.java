package com.naharoo.commons.mapstruct;

import java.util.Collections;

@PrivateApi
public final class SpringContextMappingsRegistry extends SimpleMappingsRegistry {

    @PrivateApi
    static final SpringContextMappingsRegistry INSTANCE = new SpringContextMappingsRegistry();

    private SpringContextMappingsRegistry() {
        super(Collections.emptyList());
    }
}
