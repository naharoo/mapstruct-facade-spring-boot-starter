package com.naharoo.commons.mapstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class SimpleMappingFacade implements MappingFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMappingFacade.class);

    protected SimpleMappingFacade() {
        // only for extension
    }

    @Override
    public <S, D> D map(final S source, final Class<D> destinationClass) {
        if (destinationClass == null) {
            throw new IllegalArgumentException("Mapping Destination class cannot be null");
        }

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
            LOGGER.debug("Registering {} -> {} mapping...", sourceSimpleName, destinationSimpleName);
            MappingsRegistry.register(MappingIdentifier.from(sourceClass, destinationClass), function);
            LOGGER.info("Registered {} -> {} mapping", sourceSimpleName, destinationSimpleName);
        }

        if (function == null) {
            throw new MappingNotFoundException(sourceClass, destinationClass);
        }

        //noinspection unchecked
        return (D) function.apply(source);
    }

    @Override
    public <S, D> List<D> mapAsList(final Collection<S> sources, final Class<D> destinationClass) {
        if (destinationClass == null) {
            throw new IllegalArgumentException("Mapping Destination class cannot be null");
        }

        if (sources == null) {
            return null;
        }

        if (sources.isEmpty()) {
            return new ArrayList<>();
        }

        return sources
                .stream()
                .map(source -> map(source, destinationClass))
                .collect(Collectors.toList());
    }

    @Override
    public <S, D> Set<D> mapAsSet(final Collection<S> sources, final Class<D> destinationClass) {
        if (destinationClass == null) {
            throw new IllegalArgumentException("Mapping Destination class cannot be null");
        }

        if (sources == null) {
            return null;
        }

        if (sources.isEmpty()) {
            return new HashSet<>();
        }

        return sources
                .stream()
                .map(source -> map(source, destinationClass))
                .collect(Collectors.toSet());
    }
}