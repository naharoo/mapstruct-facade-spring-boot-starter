package com.naharoo.commons.mapstruct;

@PrivateApi
final class MappingIdentifier {

    private final Class<?> source;
    private final Class<?> destination;

    private MappingIdentifier(final Class<?> source, final Class<?> destination) {
        this.source = source;
        this.destination = destination;
    }

    @PrivateApi
    static MappingIdentifier from(final Class<?> source, final Class<?> destination) {
        if (source == null) {
            throw new IllegalArgumentException("MappingIdentifier source cannot be null");
        }
        if (destination == null) {
            throw new IllegalArgumentException("MappingIdentifier destination cannot be null");
        }

        return new MappingIdentifier(source, destination);
    }

    @PrivateApi
    Class<?> getSource() {
        return source;
    }

    @PrivateApi
    Class<?> getDestination() {
        return destination;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final MappingIdentifier that = (MappingIdentifier) other;
        if (!source.equals(that.source)) {
            return false;
        }
        return destination.equals(that.destination);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + destination.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MappingIdentifier{" + "source=" + source +
                ", destination=" + destination +
                '}';
    }
}