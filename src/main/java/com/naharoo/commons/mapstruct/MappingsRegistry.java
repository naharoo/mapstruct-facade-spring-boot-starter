package com.naharoo.commons.mapstruct;

import static com.naharoo.commons.mapstruct.ClassUtils.isProxy;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PrivateApi
public final class MappingsRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingsRegistry.class);
    private static final Map<MappingIdentifier, UnaryOperator<Object>> MAPPINGS = new ConcurrentHashMap<>();

    private MappingsRegistry() {
        throw new IllegalAccessError("You will not pass!");
    }

    private static void logUnidirectionalMappingRegistrationTrace(
        final String sourceSimpleName,
        final String destinationSimpleName
    ) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Registering {} -> {} mapping...", sourceSimpleName, destinationSimpleName);
        }
    }

    private static void logUnidirectionalMappingRegistrationDebug(
        final String sourceSimpleName,
        final String destinationSimpleName
    ) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Registered {} -> {} mapping", sourceSimpleName, destinationSimpleName);
        }
    }

    @PrivateApi
    static void register(final MappingIdentifier identifier, final UnaryOperator<Object> mapper) {
        if (identifier == null) {
            throw new IllegalArgumentException("Cannot register a Mapping with null Identifier");
        }
        if (mapper == null) {
            throw new IllegalArgumentException("Cannot register null Mapping");
        }

        final String sourceSimpleName = identifier.getSource().getSimpleName();
        final String destinationSimpleName = identifier.getDestination().getSimpleName();

        logUnidirectionalMappingRegistrationTrace(sourceSimpleName, destinationSimpleName);
        MAPPINGS.put(identifier, mapper);
        logUnidirectionalMappingRegistrationDebug(sourceSimpleName, destinationSimpleName);
    }

    @PrivateApi
    static UnaryOperator<Object> retrieve(final MappingIdentifier identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Cannot get a Mapping with null Identifier");
        }
        return retrieveInternal(identifier.getSource(), identifier.getDestination());
    }

    @PrivateApi
    static Set<Entry<MappingIdentifier, UnaryOperator<Object>>> retrieveAll() {
        return MAPPINGS.entrySet();
    }

    @PrivateApi
    static boolean exists(final MappingIdentifier identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Identifier cannot be null when checking for Mapping existence");
        }

        return MAPPINGS.containsKey(identifier);
    }

    @PrivateApi
    static void clear() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clearing MappingsRegistry... All registered mappings will be removed");
        }
        MAPPINGS.clear();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Done clearing MappingsRegistry. All registered mappings are removed");
        }
    }

    private static UnaryOperator<Object> retrieveInternal(final Class<?> sourceClass, final Class<?> destinationClass) {
        final Optional<UnaryOperator<Object>> functionOpt = findMappingFunction(sourceClass, destinationClass);
        if (functionOpt.isPresent()) {
            return functionOpt.get();
        }

        if (!isProxy(sourceClass)) {
            return null;
        }

        // If the source class is a Proxy, we need to traverse and check parents tree
        // Here we're checking the superclass, mainly for CGLib Proxies
        final Class<?> sourceSuperclass = sourceClass.getSuperclass();
        if (sourceSuperclass != null) {
            final Optional<UnaryOperator<Object>> mappingFunctionOpt = findMappingFunction(sourceSuperclass, destinationClass);
            if (mappingFunctionOpt.isPresent()) {
                return mappingFunctionOpt.get();
            }
        }

        // Here we're checking superinterfaces, mainly for Dynamic Proxies
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

    private static Optional<UnaryOperator<Object>> findMappingFunction(final Class<?> sourceClass, final Class<?> destinationClass) {
        return Optional.ofNullable(MAPPINGS.get(MappingIdentifier.from(sourceClass, destinationClass)));
    }
}
