package com.naharoo.commons.mapstruct;

import static com.naharoo.commons.mapstruct.ClassUtils.extractGenericParameters;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Primary;

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
        @SuppressWarnings("unchecked")
        final BidirectionalMapper<Object, Object> castedBean = (BidirectionalMapper<Object, Object>) bean;

        final Class<?> beanClass = bean.getClass();
        final Class<?>[] genericClasses = extractGenericParameters(beanClass, BidirectionalMapper.class, 2);
        final Class<?> source = genericClasses[0];
        final Class<?> destination = genericClasses[1];

        final MappingIdentifier directMappingIdentifier = MappingIdentifier.from(source, destination);

        if (!MappingsRegistry.exists(directMappingIdentifier) || beanClass.isAnnotationPresent(Primary.class)) {
            MappingsRegistry.register(directMappingIdentifier, castedBean::map);
            MappingsRegistry.register(MappingIdentifier.from(destination, source), castedBean::mapReverse);
        }
    }

    private void processUnidirectionalMapper(final Object bean) {
        @SuppressWarnings("unchecked")
        final UnidirectionalMapper<Object, Object> castedBean = (UnidirectionalMapper<Object, Object>) bean;

        final Class<?> beanClass = bean.getClass();
        final Class<?>[] genericClasses = extractGenericParameters(beanClass, UnidirectionalMapper.class, 2);
        final Class<?> source = genericClasses[0];
        final Class<?> destination = genericClasses[1];

        final MappingIdentifier directMappingIdentifier = MappingIdentifier.from(source, destination);

        if (!MappingsRegistry.exists(directMappingIdentifier) || beanClass.isAnnotationPresent(Primary.class)) {
            MappingsRegistry.register(directMappingIdentifier, castedBean::map);
        }
    }
}
