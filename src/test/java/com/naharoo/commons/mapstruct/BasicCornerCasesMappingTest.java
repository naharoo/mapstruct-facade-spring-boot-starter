package com.naharoo.commons.mapstruct;

import com.naharoo.commons.mapstruct.mapper.basic.cornercases.Animal;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.naharoo.commons.testingtoolkit.random.RandomizationSupport.randomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("ConstantConditions")
@ComponentScan("com.naharoo.commons.mapstruct.mapper.basic.cornercases")
class BasicCornerCasesMappingTest extends AbstractMappingTest {

    @Test
    @DisplayName("When source is null, null should be returned")
    void testNullSourceMap() {
        // Given
        final Object source = null;

        // When
        final String result = mappingFacade.map(source, String.class);

        // Then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("When destinationClass is null, exception should be thrown")
    void testNullDestinationClassMap() {
        // Given
        final Object source = new Object();
        final Class<?> destinationClass = null;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> mappingFacade.map(source, destinationClass)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When source set is null, null should be returned")
    void testNullSourceMapAsSet() {

        // Given
        final Set<Object> source = null;

        // When
        final Set<String> result = mappingFacade.mapAsSet(source, String.class);

        // Then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("When destinationClass is null during mapAsSet, exception should be thrown")
    void testNullDestinationClassMapAsSet() {
        // Given
        final Set<Object> source = Collections.singleton(new Object());
        final Class<?> destinationClass = null;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> mappingFacade.mapAsSet(source, destinationClass)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When sources set is empty during mapAsSet, exception should be thrown")
    void testEmptySourcesMapAsSet() {
        // Given
        final Set<Object> source = Collections.emptySet();
        final Class<?> destinationClass = int.class;

        // When
        final Set<?> result = mappingFacade.mapAsSet(source, destinationClass);

        // Then
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("When source list is null, null should be returned")
    void testNullSourceMapAsList() {
        // Given
        final List<Object> source = null;

        // When
        final List<String> result = mappingFacade.mapAsList(source, String.class);

        // Then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("When destinationClass is null during mapAsList, exception should be thrown")
    void testNullDestinationClassMapAsList() {
        // Given
        final List<Object> source = Collections.singletonList(new Object());
        final Class<?> destinationClass = null;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> mappingFacade.mapAsList(source, destinationClass)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When sources list is empty during mapAsSet, exception should be thrown")
    void testEmptySourcesMapAsList() {
        // Given
        final List<Object> source = Collections.emptyList();
        final Class<?> destinationClass = int.class;

        // When
        final List<?> result = mappingFacade.mapAsList(source, destinationClass);

        // Then
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("When source array is null, null should be returned")
    void testNullArraySourceMapAsArray() {
        // Given
        final Object[] sources = null;

        // When
        final String[] result = mappingFacade.mapAsArray(sources, String.class);

        // Then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("When destinationClass is null during mapAsArray, exception should be thrown")
    void testArrayNullDestinationClassMapAsArray() {
        // Given
        final Object[] sources = {new Object()};
        final Class<?> destinationClass = null;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> mappingFacade.mapAsArray(sources, destinationClass)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When sources array is empty during mapAsArray, empty array should be returned")
    void testEmptySourcesArrayMapAsArray() {
        // Given
        final Object[] sources = {};
        final Class<?> destinationClass = int.class;

        // When
        final Object[] result = mappingFacade.mapAsArray(sources, destinationClass);

        // Then
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("When mapping is not registered for S -> D, exception should be thrown")
    void testMappingNotRegisteredMap() {
        // Given
        final Animal animal = new Animal(randomizer().long_(), randomizer().string());
        final Class<?> destinationClass = StringBuilder.class;

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(
            () -> mappingFacade.map(animal, destinationClass)
        );

        // Then
        throwableAssert.isNotNull().isInstanceOf(MappingNotFoundException.class);
    }
}
