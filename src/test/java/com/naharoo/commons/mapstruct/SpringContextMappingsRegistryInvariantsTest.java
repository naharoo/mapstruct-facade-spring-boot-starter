package com.naharoo.commons.mapstruct;

import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("ConstantConditions")
class SpringContextMappingsRegistryInvariantsTest {

    public static final SpringContextMappingsRegistry MAPPINGS_REGISTRY = SpringContextMappingsRegistry.INSTANCE;

    @Test
    @DisplayName("When trying to register a mapping with null Identifier, an exception should be thrown")
    void testRegisterNullIdentifier() {
        // Given
        final MappingIdentifier identifier = null;
        final UnaryOperator<Object> mapping = i -> i;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> MAPPINGS_REGISTRY.register(identifier, mapping)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When trying to register a null mapping, an exception should be thrown")
    void testRegisterNullMapping() {
        // Given
        final MappingIdentifier identifier = MappingIdentifier.from(int.class, void.class);
        final UnaryOperator<Object> mapping = null;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> MAPPINGS_REGISTRY.register(identifier, mapping)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When trying to retrieve a mapping with null Identifier, an exception should be thrown")
    void testRetrieveNullIdentifier() {
        // Given
        final MappingIdentifier identifier = null;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> MAPPINGS_REGISTRY.retrieve(identifier)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When trying to check mapping existence a mapping with null Identifier, an exception should be thrown")
    void testExistenceNullIdentifier() {
        // Given
        final MappingIdentifier identifier = null;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> MAPPINGS_REGISTRY.exists(identifier)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }
}
