package com.naharoo.commons.mapstruct;

/**
 * Mapping definitions base interface.
 * <p>
 * All mappings which are supposed to be registered must:
 * <ol>
 *     <li>Be declared as interfaces</li>
 *     <li>Extend this interface</li>
 *     <li>Be annotated with {@link org.mapstruct.Mapper} annotation</li>
 *     <li>Declare <code>"spring"</code> as their {@link org.mapstruct.Mapper#componentModel()}</li>
 * </ol>
 * </p>
 * <p>
 * MapStruct's annotation processor will generate corresponding implementations for those Mappers and annotate
 * them with {@link org.springframework.stereotype.Component} annotation.
 * Afterwards they will be picked up by {@link com.naharoo.commons.mapstruct.MappingsRegistrationBeanPostProcessor}
 * and registered in {@link com.naharoo.commons.mapstruct.MappingsRegistry}.
 * After registration, {@link com.naharoo.commons.mapstruct.MappingFacade} can use them.
 * </p>
 * <p>
 * Sample of usage:
 * <pre>{@code
 * @Mapper(componentModel = "spring")
 * public interface CarsMapper extends BaseMapper<Car, CarDto> {
 *
 * }
 * }</pre>
 * </p>
 *
 * <p>
 * No default implementation of derived methods is required, unless you need to
 * customize the mapping algorithm.
 * </p>
 *
 * <p>
 * {@link S} and {@link D} generic types' declaration order doesn't matter, both
 * combinations will be registered as legal mappings.
 * </p>
 *
 * <p>
 * Direct use of these Mappers is not recommended. They can have unexpected behavior and
 * they are not managed by this Library.
 * </p>
 * <br/>
 * @param <S> Source type of the mapping
 * @param <D> Destination type of the mapping
 * @see org.mapstruct.Mapper
 * @see org.springframework.stereotype.Component
 * @see com.naharoo.commons.mapstruct.MappingsRegistrationBeanPostProcessor
 * @see com.naharoo.commons.mapstruct.MappingsRegistry
 * @see com.naharoo.commons.mapstruct.MappingFacade
 */
public interface BaseMapper<S, D> {

    String SPRING_COMPONENT_MODEL = "spring";

    D map(S source);

    S mapReverse(D destination);
}
