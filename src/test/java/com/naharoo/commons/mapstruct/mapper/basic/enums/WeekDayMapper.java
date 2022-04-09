package com.naharoo.commons.mapstruct.mapper.basic.enums;

import com.naharoo.commons.mapstruct.BaseMapper;
import org.mapstruct.Mapper;

import static com.naharoo.commons.mapstruct.BaseMapper.SPRING_COMPONENT_MODEL;

@Mapper(componentModel = SPRING_COMPONENT_MODEL)
public interface WeekDayMapper extends BaseMapper<WeekDay, WeekDayDto> {}
