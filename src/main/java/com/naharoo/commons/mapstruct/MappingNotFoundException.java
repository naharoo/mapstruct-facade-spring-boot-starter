package com.naharoo.commons.mapstruct;

public final class MappingNotFoundException extends RuntimeException {

    private final Class<?> sourceClass;
    private final Class<?> destinationClass;

    public MappingNotFoundException(final Class<?> sourceClass, final Class<?> destinationClass) {
        super(String.format(
                "There is no Mapping registered for %s -> %s conversions.",
                sourceClass.getSimpleName(),
                destinationClass.getSimpleName()
        ));
        this.sourceClass = sourceClass;
        this.destinationClass = destinationClass;
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public Class<?> getDestinationClass() {
        return destinationClass;
    }
}
