package com.naharoo.commons.mapstruct;

import com.naharoo.commons.mapstruct.mapper.basic.finalfields.car.Car;
import com.naharoo.commons.mapstruct.mapper.basic.finalfields.car.CarDto;
import com.naharoo.commons.mapstruct.mapper.basic.finalfields.car.CarType;
import com.naharoo.commons.mapstruct.mapper.basic.finalfields.car.CarTypeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan("com.naharoo.commons.mapstruct.mapper.basic.finalfields.car")
class BasicFinalFieldsMappingTest extends AbstractMappingTest {

    @Test
    @DisplayName("When mappings are configured, S -> D mapping should map all fields")
    void testMap() {
        // Given
        final Car car = new Car(
                RANDOM.nextLong(),
                RANDOM.nextObject(String.class),
                RANDOM.nextObject(CarType.class),
                RANDOM.nextInt(),
                RANDOM.nextObject(BigDecimal.class),
                RANDOM.nextDouble()
        );

        // When
        final CarDto carDto = mappingFacade.map(car, CarDto.class);

        // Then
        assertThat(carDto).isNotNull();
        assertThat(carDto.getId()).isEqualTo(car.getId());
        assertThat(carDto.getMake()).isEqualTo(car.getMake());
        assertThat(carDto.getType()).isEqualTo(CarTypeDto.valueOf(car.getType().name()));
        assertThat(carDto.getYear()).isEqualTo(car.getYear());
        assertThat(carDto.getPrice()).isEqualTo(car.getPrice());
        assertThat(carDto.getWeight()).isEqualTo(car.getWeight());
    }

    @Test
    @DisplayName("When mappings are configured, D -> S mapping should map all fields")
    void testMapReverse() {
        // Given
        final CarDto carDto = new CarDto(
                RANDOM.nextLong(),
                RANDOM.nextObject(String.class),
                RANDOM.nextObject(CarTypeDto.class),
                RANDOM.nextInt(),
                RANDOM.nextObject(BigDecimal.class),
                RANDOM.nextDouble()
        );

        // When
        final Car car = mappingFacade.map(carDto, Car.class);

        // Then
        assertThat(car).isNotNull();
        assertThat(car.getId()).isEqualTo(carDto.getId());
        assertThat(car.getMake()).isEqualTo(carDto.getMake());
        assertThat(car.getType()).isEqualTo(CarType.valueOf(carDto.getType().name()));
        assertThat(car.getYear()).isEqualTo(carDto.getYear());
        assertThat(car.getPrice()).isEqualTo(carDto.getPrice());
        assertThat(car.getWeight()).isEqualTo(carDto.getWeight());
    }
}
