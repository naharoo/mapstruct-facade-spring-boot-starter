package com.naharoo.commons.mapstruct;

@PublicApi
public final class MappingNotFoundException extends RuntimeException {

    private final Class<?> sourceClass;
    private final Class<?> destinationClass;

    @PublicApi
    public MappingNotFoundException(final Class<?> sourceClass, final Class<?> destinationClass) {
        super(String.format(
                "There is no Mapping registered for %s -> %s conversions.",
                sourceClass.getSimpleName(),
                destinationClass.getSimpleName()
        ));
        this.sourceClass = sourceClass;
        this.destinationClass = destinationClass;
    }

    @PublicApi
    public Class<?> getSourceClass() {
        return sourceClass;
    }

    @PublicApi
    public Class<?> getDestinationClass() {
        return destinationClass;
    }
}
