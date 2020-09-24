package com.naharoo.commons.mapstruct;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

public final class MappingsRegistry {

    private static final Map<MappingIdentifier, UnaryOperator<Object>> MAPPINGS = new ConcurrentHashMap<>();

    private MappingsRegistry() {
        throw new IllegalAccessError("You will not pass!");
    }

    static void register(final MappingIdentifier identifier, final UnaryOperator<Object> mapper) {
        if (identifier == null) {
            throw new IllegalArgumentException("Cannot register a Mapping with null Identifier");
        }
        if (mapper == null) {
            throw new IllegalArgumentException("Cannot register null Mapping");
        }
        MAPPINGS.put(identifier, mapper);
    }

    static UnaryOperator<Object> retrieve(final MappingIdentifier identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Cannot get a Mapping with null Identifier");
        }
        return MAPPINGS.get(identifier);
    }

    static Set<Entry<MappingIdentifier, UnaryOperator<Object>>> retrieveAll() {
        return MAPPINGS.entrySet();
    }

    static boolean exists(final MappingIdentifier identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Identifier cannot be null when checking for Mapping existence");
        }

        return MAPPINGS.containsKey(identifier);
    }
}
