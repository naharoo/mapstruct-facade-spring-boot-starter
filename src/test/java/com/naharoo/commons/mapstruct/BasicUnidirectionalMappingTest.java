package com.naharoo.commons.mapstruct;

import com.naharoo.commons.mapstruct.mapper.unidirectional.basic.Organization;
import com.naharoo.commons.mapstruct.mapper.unidirectional.basic.OrganizationDto;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ComponentScan("com.naharoo.commons.mapstruct.mapper.unidirectional.basic")
class BasicUnidirectionalMappingTest extends AbstractMappingTest {

    @Test
    @DisplayName("When mappings are configured, S -> D mapping should be successful")
    void testMap() {
        // Given
        final Organization organization = RANDOM.nextObject(Organization.class);

        // When
        final OrganizationDto organizationDto = mappingFacade.map(organization, OrganizationDto.class);

        // Then
        assertThat(organizationDto).isNotNull().usingRecursiveComparison().isEqualTo(organization);
    }

    @Test
    @DisplayName("When mappings are configured, D -> S mapping should throw MappingNotFoundException")
    void testMapReverse() {
        // Given
        final OrganizationDto organizationDto = RANDOM.nextObject(OrganizationDto.class);

        // When
        final AbstractThrowableAssert<?, ? extends Throwable> throwableAssert = assertThatThrownBy(() -> mappingFacade
                .map(organizationDto, Organization.class));

        // Then
        throwableAssert.isNotNull().isInstanceOf(MappingNotFoundException.class);
    }
}
