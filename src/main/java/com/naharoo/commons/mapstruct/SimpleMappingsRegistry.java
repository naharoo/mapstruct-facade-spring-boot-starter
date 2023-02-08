package com.naharoo.commons.mapstruct;

import static com.naharoo.commons.mapstruct.ClassUtils.extractGenericParameters;
import static com.naharoo.commons.mapstruct.ClassUtils.isProxy;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;

public class SimpleMappingsRegistry extends MappingsRegistry {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final Map<MappingIdentifier, UnaryOperator<Object>> mappings = new ConcurrentHashMap<>();

    @PublicApi
    public SimpleMappingsRegistry(final List<Mapper> mappers) {
        initializeMappings(mappers);
    }

    @Override
    @PrivateApi
    void register(final MappingIdentifier identifier, final UnaryOperator<Object> mapper) {
        if (identifier == null) {
            throw new IllegalArgumentException("Cannot register a Mapping with null Identifier");
        }
        if (mapper == null) {
            throw new IllegalArgumentException("Cannot register null Mapping");
        }

        final String sourceSimpleName = identifier.getSource().getSimpleName();
        final String destinationSimpleName = identifier.getDestination().getSimpleName();

        logUnidirectionalMappingRegistrationTrace(sourceSimpleName, destinationSimpleName);
        mappings.put(identifier, mapper);
        logUnidirectionalMappingRegistrationDebug(sourceSimpleName, destinationSimpleName);
    }

    @Override
    @PrivateApi
    UnaryOperator<Object> retrieve(final MappingIdentifier identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Cannot get a Mapping with null Identifier");
        }
        return retrieveInternal(identifier.getSource(), identifier.getDestination());
    }

    @Override
    @PrivateApi
    Set<Entry<MappingIdentifier, UnaryOperator<Object>>> retrieveAll() {
        return mappings.entrySet();
    }

    @Override
    @PrivateApi
    boolean exists(final MappingIdentifier identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Identifier cannot be null when checking for Mapping existence");
        }

        return mappings.containsKey(identifier);
    }

    @Override
    @PrivateApi
    void clear() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clearing MappingsRegistry... All registered mappings will be removed");
        }
        mappings.clear();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Done clearing MappingsRegistry. All registered mappings are removed");
        }
    }

    private UnaryOperator<Object> retrieveInternal(final Class<?> sourceClass, final Class<?> destinationClass) {
        final Optional<UnaryOperator<Object>> functionOpt = findMappingFunction(sourceClass, destinationClass);
        if (functionOpt.isPresent()) {
            return functionOpt.get();
        }

        if (!isProxy(sourceClass)) {
            return null;
        }

        // If the source class is a Proxy, we need to traverse and check parents tree
        // Here we're checking the superclass
        // Used for CGLib and Hibernate proxies
        final Class<?> sourceSuperclass = sourceClass.getSuperclass();
        if (sourceSuperclass != null) {
            final Optional<UnaryOperator<Object>> mappingFunctionOpt = findMappingFunction(sourceSuperclass, destinationClass);
            if (mappingFunctionOpt.isPresent()) {
                return mappingFunctionOpt.get();
            }
        }

        // Here we're checking superinterfaces
        // Used for Dynamic and Javassist proxies
        final Class<?>[] sourceInterfaces = sourceClass.getInterfaces();
        for (final Class<?> sourceInterface : sourceInterfaces) {
            final Optional<UnaryOperator<Object>> mappingFunctionOpt = findMappingFunction(
                sourceInterface,
                destinationClass
            );
            if (mappingFunctionOpt.isPresent()) {
                return mappingFunctionOpt.get();
            }
        }

        return null;
    }

    private Optional<UnaryOperator<Object>> findMappingFunction(final Class<?> sourceClass, final Class<?> destinationClass) {
        return Optional.ofNullable(mappings.get(MappingIdentifier.from(sourceClass, destinationClass)));
    }

    private void logUnidirectionalMappingRegistrationTrace(
        final String sourceSimpleName,
        final String destinationSimpleName
    ) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Registering {} -> {} mapping...", sourceSimpleName, destinationSimpleName);
        }
    }

    private void logUnidirectionalMappingRegistrationDebug(
        final String sourceSimpleName,
        final String destinationSimpleName
    ) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Registered {} -> {} mapping", sourceSimpleName, destinationSimpleName);
        }
    }

    @SuppressWarnings("unchecked")
    private void initializeMappings(final List<Mapper> mappers) {
        for (final Mapper mapper : mappers) {
            if (mapper instanceof BidirectionalMapper) {
                registerBidirectionalMapper((BidirectionalMapper<Object, Object>) mapper);
            } else if (mapper instanceof UnidirectionalMapper) {
                registerUnidirectionalMapper((UnidirectionalMapper<Object, Object>) mapper);
            }
        }
    }

    private void registerUnidirectionalMapper(final UnidirectionalMapper<Object, Object> mapper) {
        final Class<?> mapperClass = mapper.getClass();
        final Class<?>[] genericClasses = extractGenericParameters(mapperClass, UnidirectionalMapper.class, 2);
        final Class<?> source = genericClasses[0];
        final Class<?> destination = genericClasses[1];

        final MappingIdentifier directMappingIdentifier = MappingIdentifier.from(source, destination);

        if (!exists(directMappingIdentifier)) {
            register(directMappingIdentifier, mapper::map);
        }
    }

    private void registerBidirectionalMapper(final BidirectionalMapper<Object, Object> mapper) {
        final Class<?> mapperClass = mapper.getClass();
        final Class<?>[] genericClasses = extractGenericParameters(mapperClass, BidirectionalMapper.class, 2);
        final Class<?> source = genericClasses[0];
        final Class<?> destination = genericClasses[1];

        final MappingIdentifier directMappingIdentifier = MappingIdentifier.from(source, destination);

        if (!exists(directMappingIdentifier)) {
            register(directMappingIdentifier, mapper::map);
            register(MappingIdentifier.from(destination, source), mapper::mapReverse);
        }
    }
}
