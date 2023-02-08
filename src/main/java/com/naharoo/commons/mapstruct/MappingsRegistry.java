package com.naharoo.commons.mapstruct;

import java.util.Map.Entry;
import java.util.Set;
import java.util.function.UnaryOperator;

public abstract class MappingsRegistry {

    @PrivateApi
    abstract void register(MappingIdentifier identifier, UnaryOperator<Object> mapper);

    @PrivateApi
    abstract UnaryOperator<Object> retrieve(MappingIdentifier identifier);

    @PrivateApi
    abstract Set<Entry<MappingIdentifier, UnaryOperator<Object>>> retrieveAll();

    @PrivateApi
    abstract boolean exists(final MappingIdentifier identifier);

    @PrivateApi
    abstract void clear();
}
