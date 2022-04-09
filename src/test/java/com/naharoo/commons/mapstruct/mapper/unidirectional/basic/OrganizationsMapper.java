package com.naharoo.commons.mapstruct.mapper.unidirectional.basic;

import com.naharoo.commons.mapstruct.UnidirectionalMapper;
import org.mapstruct.Mapper;

import static com.naharoo.commons.mapstruct.Mapper.SPRING_COMPONENT_MODEL;

@Mapper(componentModel = SPRING_COMPONENT_MODEL)
public interface OrganizationsMapper extends UnidirectionalMapper<Organization, OrganizationDto> {}
