package com.naharoo.commons.mapstruct;

import java.lang.reflect.Proxy;
import javassist.util.proxy.ProxyFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.core.GenericTypeResolver;

@PrivateApi
public final class ClassUtils {

    private static final String CGLIB_CLASS_TOKEN = "CGLIB";

    private ClassUtils() {
        throw new IllegalAccessError("You will not pass!");
    }

    @PrivateApi
    public static boolean isProxy(final Class<?> clazz) {
        return isDynamicProxy(clazz) || isCglibProxy(clazz) || isJavassistProxy(clazz) || isHibernateProxy(clazz);
    }

    @PrivateApi
    public static boolean isDynamicProxy(final Class<?> clazz) {
        return clazz != null && Proxy.isProxyClass(clazz);
    }

    @PrivateApi
    public static boolean isCglibProxy(final Class<?> clazz) {
        return clazz != null && clazz.getName().contains(CGLIB_CLASS_TOKEN);
    }

    @PrivateApi
    public static boolean isJavassistProxy(final Class<?> clazz) {
        return clazz != null && isClassPresentInClasspath("javassist.util.proxy.ProxyFactory") && ProxyFactory.isProxyClass(clazz);
    }

    @PrivateApi
    public static boolean isHibernateProxy(final Class<?> clazz) {
        return clazz != null && isClassPresentInClasspath("org.hibernate.proxy.HibernateProxy") && HibernateProxy.class.isAssignableFrom(clazz);
    }

    @PrivateApi
    public static boolean isClassPresentInClasspath(final String fullQualifiedName) {
        return isClassPresentInClasspath(fullQualifiedName, ClassUtils.class.getClassLoader())
            || isClassPresentInClasspath(fullQualifiedName, ClassLoader.getSystemClassLoader());
    }

    @PrivateApi
    public static boolean isClassPresentInClasspath(final String fullQualifiedName, final ClassLoader loader) {
        try {
            Class.forName(fullQualifiedName, false, loader);
            return true;
        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }

    @PrivateApi
    public static Class<?>[] extractGenericParameters(final Class<?> beanClass, final Class<?> genericInterface, final int numberOfParameters) {
        final Class<?>[] classes = GenericTypeResolver.resolveTypeArguments(beanClass, genericInterface);

        if (classes == null || classes.length < numberOfParameters) {
            throw new IllegalArgumentException("Failed to extract Generic Parameters from " + beanClass.getName());
        }

        return classes;
    }
}
