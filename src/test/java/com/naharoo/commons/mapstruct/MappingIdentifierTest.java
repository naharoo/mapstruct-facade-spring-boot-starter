package com.naharoo.commons.mapstruct;

import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("ConstantConditions")
class MappingIdentifierTest {

    @Test
    @DisplayName("When source is null, then IllegalArgumentException should be thrown")
    void testFromNullSource() {
        // Given
        final Class<?> source = null;
        final Class<?> destination = String.class;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
                () -> MappingIdentifier.from(source, destination)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When destination is null, then IllegalArgumentException should be thrown")
    void testFromNullDestination() {
        // Given
        final Class<?> source = String.class;
        final Class<?> destination = null;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
                () -> MappingIdentifier.from(source, destination)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When Identifier's instance is constructed, getSource should return actual source")
    void testGetSourceReturnsActualSource() {
        // Given
        final Class<?> source = String.class;
        final Class<?> destination = int.class;

        // When
        final MappingIdentifier identifier = MappingIdentifier.from(source, destination);

        // Then
        assertThat(identifier).isNotNull();
        assertThat(identifier.getSource()).isNotNull().isSameAs(source);
    }

    @Test
    @DisplayName("When Identifier's instance is constructed, getDestination should return actual destination")
    void testGetSourceReturnsActualDestination() {
        // Given
        final Class<?> source = String.class;
        final Class<?> destination = int.class;

        // When
        final MappingIdentifier identifier = MappingIdentifier.from(source, destination);

        // Then
        assertThat(identifier).isNotNull();
        assertThat(identifier.getDestination()).isNotNull().isSameAs(destination);
    }

    @Test
    void testEqualsHashCodeToString() {
        // Given
        final Class<?> source = String.class;
        final Class<?> destination = int.class;

        // When
        final MappingIdentifier firstIdentifier = MappingIdentifier.from(source, destination);
        final MappingIdentifier secondIdentifier = MappingIdentifier.from(source, destination);
        final MappingIdentifier thirdIdentifier = MappingIdentifier.from(source, void.class);

        // Then
        assertThat(firstIdentifier)
                .isNotNull()
                .isSameAs(firstIdentifier)
                .isNotSameAs(secondIdentifier)
                .isEqualTo(firstIdentifier)
                .isEqualTo(secondIdentifier)
                .isNotEqualTo(null)
                .isNotEqualTo(thirdIdentifier);
        assertThat(firstIdentifier.hashCode()).isNotNull().isEqualTo(secondIdentifier.hashCode());
        assertThat(firstIdentifier.toString())
                .isNotNull()
                .contains(source.getSimpleName())
                .contains(destination.getSimpleName())
                .isEqualTo(secondIdentifier.toString());
    }
}
