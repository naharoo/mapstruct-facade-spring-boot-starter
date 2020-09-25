package com.naharoo.commons.mapstruct.mapper.basic.cornercases;

import com.naharoo.commons.mapstruct.BaseMapper;
import com.naharoo.commons.mapstruct.mapper.basic.enums.WeekDay;
import com.naharoo.commons.mapstruct.mapper.basic.enums.WeekDayDto;
import org.mapstruct.Mapper;

import static com.naharoo.commons.mapstruct.BaseMapper.SPRING_COMPONENT_MODEL;

@Mapper(componentModel = SPRING_COMPONENT_MODEL)
public interface AnimalMapper extends BaseMapper<WeekDay, WeekDayDto> {
}
