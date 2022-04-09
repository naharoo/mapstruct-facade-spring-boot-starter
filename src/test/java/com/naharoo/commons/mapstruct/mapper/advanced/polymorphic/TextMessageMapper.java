package com.naharoo.commons.mapstruct.mapper.advanced.polymorphic;

import com.naharoo.commons.mapstruct.BaseMapper;
import org.mapstruct.Mapper;

import static com.naharoo.commons.mapstruct.BaseMapper.SPRING_COMPONENT_MODEL;

@Mapper(componentModel = SPRING_COMPONENT_MODEL, uses = MessageTypeMapper.class)
public interface TextMessageMapper extends BaseMapper<TextMessage, TextMessageDto> {}
