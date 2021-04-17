package com.naharoo.commons.mapstruct;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Primary;
import org.springframework.core.GenericTypeResolver;

@PrivateApi
final class MappingsRegistrationBeanPostProcessor implements BeanPostProcessor {

    @PrivateApi
    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
        if (!(bean instanceof Mapper)) {
            return bean;
        }

        if (bean instanceof BidirectionalMapper) {
            processBidirectionalMapper(bean);
        } else if (bean instanceof UnidirectionalMapper) {
            processUnidirectionalMapper(bean);
        }

        return bean;
    }

    private void processBidirectionalMapper(final Object bean) {
        @SuppressWarnings("unchecked") final BidirectionalMapper<Object, Object> castedBean = (BidirectionalMapper<Object, Object>) bean;

        final Class<?> beanClass = bean.getClass();
        final Class<?>[] genericClasses = extractGenericParameters(beanClass, BidirectionalMapper.class);
        final Class<?> source = genericClasses[0];
        final Class<?> destination = genericClasses[1];

        final MappingIdentifier directMappingIdentifier = MappingIdentifier.from(source, destination);

        if (!MappingsRegistry.exists(directMappingIdentifier) || beanClass.isAnnotationPresent(Primary.class)) {
            MappingsRegistry.register(directMappingIdentifier, castedBean::map);
            MappingsRegistry.register(MappingIdentifier.from(destination, source), castedBean::mapReverse);
        }
    }

    private void processUnidirectionalMapper(final Object bean) {
        @SuppressWarnings("unchecked") final UnidirectionalMapper<Object, Object> castedBean = (UnidirectionalMapper<Object, Object>) bean;

        final Class<?> beanClass = bean.getClass();
        final Class<?>[] genericClasses = extractGenericParameters(beanClass, UnidirectionalMapper.class);
        final Class<?> source = genericClasses[0];
        final Class<?> destination = genericClasses[1];

        final MappingIdentifier directMappingIdentifier = MappingIdentifier.from(source, destination);

        if (!MappingsRegistry.exists(directMappingIdentifier) || beanClass.isAnnotationPresent(Primary.class)) {
            MappingsRegistry.register(directMappingIdentifier, castedBean::map);
        }
    }

    private Class<?>[] extractGenericParameters(final Class<?> beanClass, final Class<?> genericInterface) {
        final Class<?>[] classes = GenericTypeResolver.resolveTypeArguments(beanClass, genericInterface);

        if (classes == null || classes.length < 2) {
            throw new IllegalArgumentException("Failed to extract Generic Parameters from " + beanClass.getName());
        }

        return classes;
    }
}
