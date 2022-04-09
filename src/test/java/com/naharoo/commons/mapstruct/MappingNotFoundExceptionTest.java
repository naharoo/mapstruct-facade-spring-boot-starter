package com.naharoo.commons.mapstruct;

import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MappingNotFoundExceptionTest {

    @Test
    @DisplayName("When instance is constructed, getSourceClass and getDestinationClass should work as expected")
    void testGetSourceDestinationClasses() {
        // Given
        final Class<?> sourceClass = String.class;
        final Class<?> destinationClass = int.class;
        final MappingNotFoundException exception = new MappingNotFoundException(sourceClass, destinationClass);

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> {
                throw exception;
            }
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(MappingNotFoundException.class).hasMessageContainingAll(
            sourceClass.getSimpleName(),
            destinationClass.getSimpleName()
        );
        assertThat(exception.getSourceClass()).isEqualTo(sourceClass);
        assertThat(exception.getDestinationClass()).isEqualTo(destinationClass);
    }
}
