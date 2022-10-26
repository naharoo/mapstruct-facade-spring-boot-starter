package com.naharoo.commons.mapstruct;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

import com.naharoo.commons.mapstruct.mapper.proxies.sourceclass.Laptop;
import com.naharoo.commons.mapstruct.mapper.proxies.sourceclass.LaptopProxyAbstractClass;
import com.naharoo.commons.mapstruct.mapper.proxies.sourceclass.LaptopProxyInterface;
import java.lang.reflect.Proxy;
import java.util.UUID;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.naharoo.commons.mapstruct.mapper.proxies.sourceclass")
class ProxySourceClassMappingTest extends AbstractMappingTest {

    @Test
    @DisplayName("When source is a dynamic proxy, S -> D mapping should map all proxy field values")
    void testDynamicProxySource() {
        // Given
        final String brand = UUID.randomUUID().toString();
        final String name = UUID.randomUUID().toString();
        final LaptopProxyInterface proxySource = (LaptopProxyInterface) Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            new Class[] {LaptopProxyInterface.class},
            (proxyObject, method, args) -> {
                final String methodName = method.getName();
                if (methodName.equals("getBrand")) {
                    return brand;
                }
                if (methodName.equals("getName")) {
                    return name;
                }
                throw new UnsupportedOperationException("Not supported use-case");
            }
        );

        // When
        final Laptop result = mappingFacade.map(proxySource, Laptop.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo(brand);
        assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("When source is a CGLib proxy interface, S -> D mapping should map all proxy field values")
    void testCGLibProxyInterfaceSource() {
        // Given
        final String brand = UUID.randomUUID().toString();
        final String name = UUID.randomUUID().toString();

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(LaptopProxyInterface.class);
        enhancer.setCallback((InvocationHandler) (proxyObject, method, args) -> {
            final String methodName = method.getName();
            if (methodName.equals("getBrand")) {
                return brand;
            }
            if (methodName.equals("getName")) {
                return name;
            }
            throw new UnsupportedOperationException("Not supported use-case");
        });
        final LaptopProxyInterface proxySource = (LaptopProxyInterface) enhancer.create();

        // When
        final Laptop result = mappingFacade.map(proxySource, Laptop.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo(brand);
        assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("When source is a CGLib proxy abstract class, S -> D mapping should map all proxy field values")
    void testCGLibProxyAbstractClassSource() {
        // Given
        final String brand = UUID.randomUUID().toString();
        final String name = UUID.randomUUID().toString();

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(LaptopProxyAbstractClass.class);
        enhancer.setCallback((InvocationHandler) (proxyObject, method, args) -> {
            final String methodName = method.getName();
            if (methodName.equals("getBrand")) {
                return brand;
            }
            if (methodName.equals("getName")) {
                return name;
            }
            throw new UnsupportedOperationException("Not supported use-case");
        });
        final LaptopProxyAbstractClass proxySource = (LaptopProxyAbstractClass) enhancer.create();

        // When
        final Laptop result = mappingFacade.map(proxySource, Laptop.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo(brand);
        assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("When source is a Javassist proxy interface, S -> D mapping should map all proxy field values")
    void testJavassistProxyInterfaceSource() throws InstantiationException, IllegalAccessException {
        // Given
        final String brand = UUID.randomUUID().toString();
        final String name = UUID.randomUUID().toString();

        final ProxyFactory factory = new ProxyFactory();
        factory.setInterfaces(new Class[] {LaptopProxyInterface.class});
        final Class<?> clazz = factory.createClass();
        final Object instance = clazz.newInstance();
        ((ProxyObject) instance).setHandler((self, method, forwarder, args) -> {
            final String methodName = method.getName();
            if (methodName.equals("getBrand")) {
                return brand;
            }
            if (methodName.equals("getName")) {
                return name;
            }
            throw new UnsupportedOperationException("Not supported use-case");
        });
        final LaptopProxyInterface proxySource = (LaptopProxyInterface) instance;

        // When
        final Laptop result = mappingFacade.map(proxySource, Laptop.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo(brand);
        assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("When source is a Javassist proxy abstract class, S -> D mapping should map all proxy field values")
    void testJavassistProxyAbstractClassSource() throws InstantiationException, IllegalAccessException {
        // Given
        final String brand = UUID.randomUUID().toString();
        final String name = UUID.randomUUID().toString();

        final ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(LaptopProxyAbstractClass.class);
        final Class<?> clazz = factory.createClass();
        final Object instance = clazz.newInstance();
        ((ProxyObject) instance).setHandler((self, method, forwarder, args) -> {
            final String methodName = method.getName();
            if (methodName.equals("getBrand")) {
                return brand;
            }
            if (methodName.equals("getName")) {
                return name;
            }
            throw new UnsupportedOperationException("Not supported use-case");
        });
        final LaptopProxyAbstractClass proxySource = (LaptopProxyAbstractClass) instance;

        // When
        final Laptop result = mappingFacade.map(proxySource, Laptop.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo(brand);
        assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("When source is a Hibernate proxy class, S -> D mapping should map all proxy field values")
    void testHibernateProxyClassSource() throws InstantiationException, IllegalAccessException {
        // Given
        final String brand = UUID.randomUUID().toString();
        final String name = UUID.randomUUID().toString();

        final ByteBuddy byteBuddy = new ByteBuddy();
        Builder<LaptopProxyAbstractClass> builder = byteBuddy.subclass(LaptopProxyAbstractClass.class).implement(HibernateProxy.class).method(
            any()
        ).intercept(
            InvocationHandlerAdapter.of((proxyObject, method, args) -> {
                final String methodName = method.getName();
                if (methodName.equals("writeReplace")) {
                    return null;
                }
                if (methodName.equals("getHibernateLazyInitializer")) {
                    return null;
                }
                if (methodName.equals("getBrand")) {
                    return brand;
                }
                if (methodName.equals("getName")) {
                    return name;
                }
                throw new UnsupportedOperationException("Not supported use-case");
            })
        );

        final Unloaded<LaptopProxyAbstractClass> unloadedProxy = builder.make();
        final Loaded<LaptopProxyAbstractClass> loadedProxy = unloadedProxy.load(this.getClass().getClassLoader());
        final Class<? extends LaptopProxyAbstractClass> clazz = loadedProxy.getLoaded();
        final LaptopProxyAbstractClass proxySource = clazz.newInstance();

        // When
        final Laptop result = mappingFacade.map(proxySource, Laptop.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo(brand);
        assertThat(result.getName()).isEqualTo(name);
    }
}
