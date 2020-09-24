package com.naharoo.commons.mapstruct;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Primary;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@PrivateApi
final class MappingsRegistrationBeanPostProcessor implements BeanPostProcessor {

    @PrivateApi
    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
        if (!(bean instanceof BaseMapper)) {
            return bean;
        }

        @SuppressWarnings("unchecked") final BaseMapper<Object, Object> castedBean = (BaseMapper<Object, Object>) bean;

        final Class<?> beanClass = bean.getClass();
        final Type[] genericTypes = extractGenericParameters(beanClass);
        final Class<?> source = (Class<?>) genericTypes[0];
        final Class<?> destination = (Class<?>) genericTypes[1];

        final MappingIdentifier directMappingIdentifier = MappingIdentifier.from(source, destination);

        if (!MappingsRegistry.exists(directMappingIdentifier) || beanClass.isAnnotationPresent(Primary.class)) {
            MappingsRegistry.register(directMappingIdentifier, castedBean::map);
            MappingsRegistry.register(MappingIdentifier.from(destination, source), castedBean::mapReverse);
        }

        return bean;
    }

    private Type[] extractGenericParameters(final Class<?> beanClass) {
        final Class<?> firstAnnotatedInterfaceClass = (Class<?>) beanClass.getAnnotatedInterfaces()[0].getType();
        final ParameterizedType firstGenericSuperInterfaceType = (ParameterizedType) firstAnnotatedInterfaceClass.getGenericInterfaces()[0];
        return firstGenericSuperInterfaceType.getActualTypeArguments();
    }
}