package com.naharoo.commons.mapstruct;

import com.naharoo.commons.mapstruct.mapper.basic.enums.WeekDay;
import com.naharoo.commons.mapstruct.mapper.basic.enums.WeekDayDto;
import com.naharoo.commons.mapstruct.mapper.basic.finalfields.car.Car;
import com.naharoo.commons.mapstruct.mapper.basic.finalfields.car.CarDto;
import com.naharoo.commons.mapstruct.mapper.basic.finalfields.car.CarType;
import com.naharoo.commons.mapstruct.mapper.basic.finalfields.car.CarTypeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan("com.naharoo.commons.mapstruct.mapper.basic.enums")
class BasicEnumsMappingTest extends AbstractMappingTest {

    @Test
    @DisplayName("When mappings are configured, S -> D mapping should map all enum values")
    void testMap() {
        // Given
        for (final WeekDay weekDay : WeekDay.values()) {
            // When
            final WeekDayDto weekDayDto = mappingFacade.map(weekDay, WeekDayDto.class);
            // Then
            assertThat(weekDayDto).isNotNull().isEqualTo(WeekDayDto.valueOf(weekDay.name()));
        }
    }

    @Test
    @DisplayName("When mappings are configured, S -> D mapping should map all enum values")
    void testMapReverse() {
        // Given
        for (final WeekDayDto weekDayDto : WeekDayDto.values()) {
            // When
            final WeekDay weekDay = mappingFacade.map(weekDayDto, WeekDay.class);
            // Then
            assertThat(weekDay).isNotNull().isEqualTo(WeekDay.valueOf(weekDayDto.name()));
        }
    }

    @Test
    @DisplayName("When mappings are configured, Set<S> -> Set<D> mapping should map all enum values")
    void testMapSet() {
        // Given
        final Set<WeekDay> weekDays = Arrays.stream(WeekDay.values()).collect(Collectors.toSet());

        // When
        final Set<WeekDayDto> weekDayDtos = mappingFacade.mapAsSet(weekDays, WeekDayDto.class);

        // Then
        assertThat(weekDayDtos).isNotNull().isNotEmpty().size().isEqualTo(weekDays.size());

        final Set<String> names = weekDays.stream().map(Enum::name).collect(Collectors.toSet());
        for (final WeekDayDto weekDayDto : weekDayDtos) {
            final String name = weekDayDto.name();
            assertThat(names).contains(name);
        }
    }

    @Test
    @DisplayName("When mappings are configured, Set<D> -> Set<S> mapping should map all enum values")
    void testMapReverseSet() {
        // Given
        final Set<WeekDayDto> weekDayDtos = Arrays.stream(WeekDayDto.values()).collect(Collectors.toSet());

        // When
        final Set<WeekDay> weekDays = mappingFacade.mapAsSet(weekDayDtos, WeekDay.class);

        // Then
        assertThat(weekDays).isNotNull().isNotEmpty().size().isEqualTo(weekDayDtos.size());

        final Set<String> names = weekDayDtos.stream().map(Enum::name).collect(Collectors.toSet());
        for (final WeekDay weekDay : weekDays) {
            final String name = weekDay.name();
            assertThat(names).contains(name);
        }
    }

    @Test
    @DisplayName("When mappings are configured, List<S> -> List<D> mapping should map all enum values")
    void testMapList() {
        // Given
        final List<WeekDay> weekDays = Arrays.stream(WeekDay.values()).collect(Collectors.toList());

        // When
        final List<WeekDayDto> weekDayDtos = mappingFacade.mapAsList(weekDays, WeekDayDto.class);

        // Then
        assertThat(weekDayDtos).isNotNull().isNotEmpty().size().isEqualTo(weekDays.size());

        final List<String> names = weekDays.stream().map(Enum::name).collect(Collectors.toList());
        for (final WeekDayDto weekDayDto : weekDayDtos) {
            final String name = weekDayDto.name();
            assertThat(names).contains(name);
        }
    }

    @Test
    @DisplayName("When mappings are configured, List<D> -> List<S> mapping should map all enum values")
    void testMapReverseList() {
        // Given
        final List<WeekDayDto> weekDayDtos = Arrays.stream(WeekDayDto.values()).collect(Collectors.toList());

        // When
        final List<WeekDay> weekDays = mappingFacade.mapAsList(weekDayDtos, WeekDay.class);

        // Then
        assertThat(weekDays).isNotNull().isNotEmpty().size().isEqualTo(weekDayDtos.size());

        final List<String> names = weekDayDtos.stream().map(Enum::name).collect(Collectors.toList());
        for (final WeekDay weekDay : weekDays) {
            final String name = weekDay.name();
            assertThat(names).contains(name);
        }
    }
}
