package com.naharoo.commons.mapstruct.mapper.basic.finalfields.car;

import com.naharoo.commons.mapstruct.BaseMapper;
import org.mapstruct.Mapper;

import static com.naharoo.commons.mapstruct.BaseMapper.SPRING_COMPONENT_MODEL;

@Mapper(componentModel = SPRING_COMPONENT_MODEL, uses = CarTypeMapper.class)
public interface CarMapper extends BaseMapper<Car, CarDto> {}
