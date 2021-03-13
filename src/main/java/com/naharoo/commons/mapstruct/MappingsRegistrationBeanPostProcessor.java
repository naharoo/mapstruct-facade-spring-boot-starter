package com.naharoo.commons.mapstruct;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Primary;
import org.springframework.core.GenericTypeResolver;

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
        final Class<?>[] genericClasses = extractGenericParameters(beanClass);
        final Class<?> source = genericClasses[0];
        final Class<?> destination = genericClasses[1];

        final MappingIdentifier directMappingIdentifier = MappingIdentifier.from(source, destination);

        if (!MappingsRegistry.exists(directMappingIdentifier) || beanClass.isAnnotationPresent(Primary.class)) {
            MappingsRegistry.register(directMappingIdentifier, castedBean::map);
            MappingsRegistry.register(MappingIdentifier.from(destination, source), castedBean::mapReverse);
        }

        return bean;
    }

    private Class<?>[] extractGenericParameters(Class<?> beanClass) {
        final Class<?>[] classes = GenericTypeResolver.resolveTypeArguments(beanClass, BaseMapper.class);

        if (classes == null || classes.length < 2) {
            throw new IllegalArgumentException("Failed to extract Generic Parameters from " + beanClass.getName());
        }

        return classes;
    }
}
