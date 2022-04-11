package com.naharoo.commons.mapstruct.mapper.proxies.sourceclass;

import static com.naharoo.commons.mapstruct.Mapper.SPRING_COMPONENT_MODEL;

import com.naharoo.commons.mapstruct.UnidirectionalMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING_COMPONENT_MODEL)
public interface LaptopProxyInterfaceMapper extends UnidirectionalMapper<LaptopProxyInterface, Laptop> {}
