package com.naharoo.commons.mapstruct;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MappingFacade {

    /**
     * Maps <code>source</code> object into <code>destinationClass</code> type.
     *
     * @param source           Original source Object which will be mapped into <code>destinationClass</code>
     * @param destinationClass <code>destinationClass</code> which is used as a metadata to resolve target type
     * @param <S>              Original source Object's Java type
     * @param <D>              Mapping destination Object's Java type
     * @return mapped destination object or null, if provided <code>source</code> was also null
     * @throws IllegalArgumentException if <code>destinationClass</code> is null
     * @throws MappingNotFoundException if there is no <code>S</code> -> <code>D</code> mapping registered
     */
    @Nullable
    <S, D> D map(@Nullable S source, @NonNull Class<D> destinationClass);

    /**
     * Maps <code>sources</code> list into a list of objects having <code>destinationClass</code> type.
     *
     * @param sources          Original list of source Objects which will be mapped into a list of objects having
     *                         <code>destinationClass</code> type
     * @param destinationClass <code>destinationClass</code> which is used as a metadata to resolve target Lists
     *                         generic type
     * @param <S>              Original source Objects List's Java generic type
     * @param <D>              Mapping destination Objects List's Java generic type
     * @return mapped destination objects list or null, if provided <code>sources</code> was also null
     * @throws IllegalArgumentException if <code>destinationClass</code> is null
     * @throws MappingNotFoundException if there is no <code>S</code> -> <code>D</code> mapping registered
     */
    @Nullable
    <S, D> List<D> mapAsList(@Nullable Collection<S> sources, @NonNull Class<D> destinationClass);

    /**
     * Maps <code>sources</code> set into a set of objects having <code>destinationClass</code> type.
     *
     * @param sources          Original set of source Objects which will be mapped into a set of objects having
     *                         <code>destinationClass</code> type
     * @param destinationClass <code>destinationClass</code> which is used as a metadata to resolve target Sets
     *                         generic type
     * @param <S>              Original source Objects Set's Java generic type
     * @param <D>              Mapping destination Objects Set's Java generic type
     * @return mapped destination objects set or null, if provided <code>sources</code> was also null
     * @throws IllegalArgumentException if <code>destinationClass</code> is null
     * @throws MappingNotFoundException if there is no <code>S</code> -> <code>D</code> mapping registered
     */
    @Nullable
    <S, D> Set<D> mapAsSet(@Nullable Collection<S> sources, @NonNull Class<D> destinationClass);
}
