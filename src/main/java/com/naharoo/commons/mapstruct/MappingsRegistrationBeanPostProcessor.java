package com.naharoo.commons.mapstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Primary;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

final class MappingsRegistrationBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingsRegistrationBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
        if (!(bean instanceof BaseMapper)) {
            return bean;
        }

        //noinspection unchecked
        final BaseMapper<Object, Object> castedBean = (BaseMapper<Object, Object>) bean;

        final Class<?> beanClass = bean.getClass();
        final Type[] genericTypes = extractGenericParameters(beanClass);
        final Type source = genericTypes[0];
        final Type destination = genericTypes[1];

        final MappingIdentifier directMappingIdentifier = MappingIdentifier.from(
                (Class<?>) source,
                (Class<?>) destination
        );

        if (!MappingsRegistry.exists(directMappingIdentifier) || beanClass.isAnnotationPresent(Primary.class)) {
            final String sourceSimpleName = ((Class<?>) source).getSimpleName();
            final String destinationSimpleName = ((Class<?>) destination).getSimpleName();
            LOGGER.debug("Registering {} <-> {} mapping...", sourceSimpleName, destinationSimpleName);

            MappingsRegistry.register(directMappingIdentifier, castedBean::map);
            MappingsRegistry.register(
                    MappingIdentifier.from((Class<?>) destination, (Class<?>) source),
                    castedBean::mapReverse
            );

            LOGGER.info("Registered {} <-> {} mapping", sourceSimpleName, destinationSimpleName);
        }

        return bean;
    }

    private Type[] extractGenericParameters(final Class<?> beanClass) {
        final Class<?> firstAnnotatedInterfaceClass = (Class<?>) beanClass.getAnnotatedInterfaces()[0].getType();
        final ParameterizedType firstGenericSuperInterfaceType = (ParameterizedType) firstAnnotatedInterfaceClass.getGenericInterfaces()[0];
        return firstGenericSuperInterfaceType.getActualTypeArguments();
    }
}