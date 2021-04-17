package com.naharoo.commons.mapstruct;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Main usage and entry point of Library.
 * Provides a mapping facade based on mapping metadata collected earlier
 * from {@link com.naharoo.commons.mapstruct.BaseMapper} extensions/children.
 * <p>
 * This interface is Spring-friendly. It can be easily injected into any business bean.
 * Avoid injecting and using this Bean before Spring ApplicationContext's complete initialization.
 * <p>
 * Uses {@link com.naharoo.commons.mapstruct.MappingsRegistrationBeanPostProcessor}
 * underneath to collect mapping metadata in Spring ApplicationContext's initialization phase.
 *
 * @see com.naharoo.commons.mapstruct.BaseMapper
 * @see com.naharoo.commons.mapstruct.MappingsRegistrationBeanPostProcessor
 */
@PublicApi
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
    @PublicApi
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
    @PublicApi
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
    @PublicApi
    <S, D> Set<D> mapAsSet(@Nullable Collection<S> sources, @NonNull Class<D> destinationClass);

    /**
     * Maps <code>sources</code> set into an array of objects having <code>destinationClass</code> type.
     *
     * @param sources          Original collection of source Objects which will be mapped into an array of objects having
     *                         <code>destinationClass</code> type
     * @param destinationClass <code>destinationClass</code> which is used as a metadata to resolve target Arrays
     *                         generic type
     * @param <S>              Original source Objects Collection's Java generic type
     * @param <D>              Mapping destination Objects Array's Java generic type
     * @return mapped destination objects array or null, if provided <code>sources</code> was also null
     * @throws IllegalArgumentException if <code>destinationClass</code> is null
     * @throws MappingNotFoundException if there is no <code>S</code> -> <code>D</code> mapping registered
     */
    @PublicApi
    <S, D> D[] mapAsArray(@Nullable Collection<S> sources, @NonNull Class<D> destinationClass);

    /**
     * Maps <code>sources</code> array into an array of objects having <code>destinationClass</code> type.
     *
     * @param sources          Original array of source Objects which will be mapped into an array of objects having
     *                         <code>destinationClass</code> type
     * @param destinationClass <code>destinationClass</code> which is used as a metadata to resolve target Arrays
     *                         generic type
     * @param <S>              Original source Objects Array's Java generic type
     * @param <D>              Mapping destination Objects Array's Java generic type
     * @return mapped destination objects array or null, if provided <code>sources</code> was also null
     * @throws IllegalArgumentException if <code>destinationClass</code> is null
     * @throws MappingNotFoundException if there is no <code>S</code> -> <code>D</code> mapping registered
     */
    @PublicApi
    <S, D> D[] mapAsArray(@Nullable S[] sources, @NonNull Class<D> destinationClass);
}
