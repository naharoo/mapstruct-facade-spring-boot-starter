package com.naharoo.commons.mapstruct;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import java.util.UUID;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

@SuppressWarnings("ConstantConditions")
class ClassUtilsTest {

    @Test
    @DisplayName("When a non-proxy class is provided, false should be returned")
    void isProxy() {
        // Given
        final Class<?> clazz = Object.class;

        // When
        final boolean result = ClassUtils.isProxy(clazz);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("When a valid dynamic proxy is provided, true should be returned")
    void isDynamicProxy() {
        // Given
        final Runnable proxy = (Runnable) Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            new Class[] {Runnable.class},
            (proxyObject, method, args) -> null
        );
        final Class<?> clazz = proxy.getClass();

        // When
        final boolean result = ClassUtils.isDynamicProxy(clazz);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("When a not valid dynamic proxy is provided, false should be returned")
    void isNotDynamicProxy() {
        // Given
        final Class<Object> clazz = Object.class;

        // When
        final boolean result = ClassUtils.isDynamicProxy(clazz);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("When a valid CGLib proxy is provided, true should be returned")
    void isCglibProxy() {
        // Given
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Runnable.class);
        enhancer.setCallback((InvocationHandler) (proxyObject, method, args) -> null);
        final Runnable proxy = (Runnable) enhancer.create();
        final Class<?> clazz = proxy.getClass();

        // When
        final boolean result = ClassUtils.isCglibProxy(clazz);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("When a not valid CGLib proxy is provided, false should be returned")
    void isNotCglibProxy() {
        // Given
        final Class<Object> clazz = Object.class;

        // When
        final boolean result = ClassUtils.isCglibProxy(clazz);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("When null is provided, false should be returned")
    void isNotCglibProxyWhenNull() {
        // Given
        final Class<Object> clazz = null;

        // When
        final boolean result = ClassUtils.isCglibProxy(clazz);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("When a valid Javassist proxy is provided, true should be returned")
    void isJavassistProxy() throws InstantiationException, IllegalAccessException {
        // Given
        final ProxyFactory factory = new ProxyFactory();
        factory.setInterfaces(new Class[] {Runnable.class});
        final Class<?> clazz = factory.createClass();
        final Object instance = clazz.newInstance();
        ((ProxyObject) instance).setHandler((self, method, forwarder, args) -> null);

        // When
        final boolean result = ClassUtils.isJavassistProxy(clazz);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("When a not valid Javassist proxy is provided, false should be returned")
    void isNotJavassistProxy() {
        // Given
        final Class<Object> clazz = Object.class;

        // When
        final boolean result = ClassUtils.isJavassistProxy(clazz);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("When null is provided, false should be returned")
    void isNotJavassistProxyWhenNull() {
        // Given
        final Class<Object> clazz = null;

        // When
        final boolean result = ClassUtils.isJavassistProxy(clazz);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("When existing class name is provided, true should be returned")
    void isClassPresentInClasspathWhenValidName() {
        // Given
        // no initial config

        // When
        final boolean result = ClassUtils.isClassPresentInClasspath("java.lang.String");

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("When not existing class name is provided, true should be returned")
    void isClassPresentInClasspathWhenNotValidName() {
        // Given
        // no initial config

        // When
        final boolean result = ClassUtils.isClassPresentInClasspath(UUID.randomUUID().toString());

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("When String class is provided for Comparable generic interface, String class should be returned")
    void extractGenericParameters() {
        // Given
        final Class<String> beanClass = String.class;
        final Class<?> genericInterface = Comparable.class;

        // When
        final Class<?>[] genericParameters = ClassUtils.extractGenericParameters(beanClass, genericInterface, 1);

        // Then
        assertThat(genericParameters).isNotNull().isNotEmpty().contains(String.class);
    }
}
