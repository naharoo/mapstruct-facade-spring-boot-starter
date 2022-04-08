package com.naharoo.commons.mapstruct;

import com.naharoo.commons.mapstruct.mapper.basic.projections.BikeDto;
import com.naharoo.commons.mapstruct.mapper.basic.projections.BikeProjectionImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan("com.naharoo.commons.mapstruct.mapper.basic.projections")
class BasicProjectionMappingTest extends AbstractMappingTest {

  @Test
  @DisplayName("When mappings are configured, S -> D mapping should map all fields")
  void testMapSToD() {
    // Given
    final BikeProjectionImpl bike =
        new BikeProjectionImpl(
            RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextLong());

    // When
    final BikeDto bikeDto = mappingFacade.map(bike, BikeDto.class);

    // Then
    assertThat(bikeDto).isNotNull();
    assertThat(bikeDto.getBrand()).isEqualTo(bike.getBrand());
    assertThat(bikeDto.getSize()).isEqualTo(bike.getSize());
    assertThat(bikeDto.getPrice()).isEqualTo(bike.getPrice());
  }

  @Test
  @DisplayName("When mappings are configured, Set<S> -> Set<D> mapping should map all fields")
  void mapMapSetOfDToSetOfS() {
    // Given
    final Set<BikeProjectionImpl> bikes = new HashSet<>();
    bikes.add(
        new BikeProjectionImpl(
            RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextLong()));
    bikes.add(
        new BikeProjectionImpl(
            RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextLong()));
    bikes.add(
        new BikeProjectionImpl(
            RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextLong()));

    // When
    final Set<BikeDto> bikeDtos = mappingFacade.mapAsSet(bikes, BikeDto.class);

    // Then
    assertThat(bikeDtos).isNotNull().isNotEmpty().size().isEqualTo(bikes.size());

    bikeDtos.forEach(
        dto ->
            assertThat(
                    bikes.contains(
                        new BikeProjectionImpl(dto.getBrand(), dto.getSize(), dto.getPrice())))
                .isTrue());
    bikes.forEach(
        bike ->
            assertThat(
                    bikeDtos.contains(
                        new BikeDto(bike.getBrand(), bike.getSize(), bike.getPrice())))
                .isTrue());
  }

}
