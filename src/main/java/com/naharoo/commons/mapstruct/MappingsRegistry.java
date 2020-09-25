package com.naharoo.commons.mapstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

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
        return MAPPINGS.get(identifier);
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
}
