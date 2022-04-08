package com.naharoo.commons.mapstruct.mapper.basic.projections;

import com.naharoo.commons.mapstruct.UnidirectionalMapper;
import org.mapstruct.Mapper;

import static com.naharoo.commons.mapstruct.BaseMapper.SPRING_COMPONENT_MODEL;

@Mapper(componentModel = SPRING_COMPONENT_MODEL)
public interface BikeMapper extends UnidirectionalMapper<BikeProjection, BikeDto> {
}
