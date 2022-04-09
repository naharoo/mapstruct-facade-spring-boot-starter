package com.naharoo.commons.mapstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@PublicApi
public class SimpleMappingFacade implements MappingFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMappingFacade.class);

    @PublicApi
    protected SimpleMappingFacade() {
        // only for extension
    }

    private static void logMappingEntranceTraceLog(final Object source, final Class<?> destinationClass) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(
                "Trying to map Object of type '{}' into '{}'...",
                source == null ? "null" : source.getClass().getSimpleName(),
                destinationClass.getSimpleName()
            );
        }
    }

    private static void logExitingDebugLog(
        final String sourceSimpleName,
        final String destinationSimpleName,
        final long totalTimeMillis
    ) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
                "Successfully mapped Object of type '{}' into '{}' in {}ms.",
                sourceSimpleName,
                destinationSimpleName,
                totalTimeMillis
            );
        }
    }

    private static void assertDestinationClass(final Class<?> destinationClass) {
        if (destinationClass == null) {
            throw new IllegalArgumentException("Mapping Destination class cannot be null");
        }
    }

    private static void assertDestinationCustomizer(final Consumer<?> destinationCustomizer) {
        if (destinationCustomizer == null) {
            throw new IllegalArgumentException("Mapping Destination Customizer cannot be null");
        }
    }

    private static <D> Class<?> wrapPrimitive(final Class<D> primitiveClass) {
        return MethodType.methodType(primitiveClass).wrap().returnType();
    }

    private static <D> Class<?> wrapIfNeeded(final Class<D> destinationClass) {
        return destinationClass.isPrimitive()
            ? wrapPrimitive(destinationClass)
            : destinationClass;
    }

    @PublicApi
    @Override
    public <S, D> D map(final S source, final Class<D> destinationClass) {
        assertDestinationClass(destinationClass);
        logMappingEntranceTraceLog(source, destinationClass);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        if (source == null) {
            return null;
        }

        final Class<?> sourceClass = source.getClass();
        final MappingIdentifier identifier = MappingIdentifier.from(sourceClass, destinationClass);
        UnaryOperator<Object> function = MappingsRegistry.retrieve(identifier);

        boolean doCache = false;
        if (function == null) {
            for (final Map.Entry<MappingIdentifier, UnaryOperator<Object>> entry : MappingsRegistry.retrieveAll()) {
                final Class<?> fromClass = entry.getKey().getSource();
                final Class<?> toClass = entry.getKey().getDestination();

                if (destinationClass.isAssignableFrom(toClass) && fromClass.equals(sourceClass)) {
                    function = entry.getValue();

                    if (function != null) {
                        doCache = true;
                    }

                    break;
                }
            }
        }

        final String sourceSimpleName = sourceClass.getSimpleName();
        final String destinationSimpleName = destinationClass.getSimpleName();

        if (doCache) {
            MappingsRegistry.register(MappingIdentifier.from(sourceClass, destinationClass), function);
        }

        if (function == null) {
            throw new MappingNotFoundException(sourceClass, destinationClass);
        }

        @SuppressWarnings("unchecked")
        final D result = (D) function.apply(source);

        stopWatch.stop();
        logExitingDebugLog(sourceSimpleName, destinationSimpleName, stopWatch.getTotalTimeMillis());
        return result;
    }

    @PublicApi
    @Override
    public <S, D> D map(
        final S source,
        final Class<D> destinationClass,
        final Consumer<D> destinationCustomizer
    ) {
        assertDestinationCustomizer(destinationCustomizer);

        final D destination = map(source, destinationClass);
        if (destination == null) {
            return null;
        }

        destinationCustomizer.accept(destination);

        return destination;
    }

    @PublicApi
    @Override
    public <S, D> List<D> mapAsList(final Collection<S> sources, final Class<D> destinationClass) {
        assertDestinationClass(destinationClass);

        if (sources == null) {
            return null;
        }

        if (sources.isEmpty()) {
            return new ArrayList<>();
        }

        return sources.stream().map(source -> map(source, destinationClass)).collect(Collectors.toList());
    }

    @PublicApi
    @Override
    public <S, D> List<D> mapAsList(
        final Collection<S> sources,
        final Class<D> destinationClass,
        final Consumer<D> destinationCustomizer
    ) {
        assertDestinationCustomizer(destinationCustomizer);

        final List<D> destinations = mapAsList(sources, destinationClass);
        if (destinations == null) {
            return null;
        }

        return destinations.stream().peek(destinationCustomizer).collect(Collectors.toList());
    }

    @PublicApi
    @Override
    public <S, D> Set<D> mapAsSet(final Collection<S> sources, final Class<D> destinationClass) {
        assertDestinationClass(destinationClass);

        if (sources == null) {
            return null;
        }

        if (sources.isEmpty()) {
            return new HashSet<>();
        }

        return sources.stream().map(source -> map(source, destinationClass)).collect(Collectors.toSet());
    }

    @PublicApi
    @Override
    public <S, D> Set<D> mapAsSet(
        final Collection<S> sources,
        final Class<D> destinationClass,
        final Consumer<D> destinationCustomizer
    ) {
        assertDestinationCustomizer(destinationCustomizer);

        final Set<D> destinations = mapAsSet(sources, destinationClass);
        if (destinations == null) {
            return null;
        }

        return destinations.stream().peek(destinationCustomizer).collect(Collectors.toSet());
    }

    @PublicApi
    @SuppressWarnings("unchecked")
    @Override
    public <S, D> D[] mapAsArray(final Collection<S> sources, final Class<D> destinationClass) {
        assertDestinationClass(destinationClass);

        if (sources == null) {
            return null;
        }

        final int size = sources.size();
        final D[] destinations = (D[]) Array.newInstance(wrapIfNeeded(destinationClass), size);

        int i = 0;
        for (final S source : sources) {
            destinations[i++] = map(source, destinationClass);
        }

        return destinations;
    }

    @PublicApi
    @SuppressWarnings("unchecked")
    @Override
    public <S, D> D[] mapAsArray(final S[] sources, final Class<D> destinationClass) {
        assertDestinationClass(destinationClass);

        if (sources == null) {
            return null;
        }

        final int size = sources.length;
        final D[] destinations = (D[]) Array.newInstance(wrapIfNeeded(destinationClass), size);

        for (int i = 0; i < size; i++) {
            destinations[i] = map(sources[i], destinationClass);
        }

        return destinations;
    }
}
