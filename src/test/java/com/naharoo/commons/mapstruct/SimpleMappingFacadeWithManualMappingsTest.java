package com.naharoo.commons.mapstruct;

import static com.naharoo.commons.testingtoolkit.random.RandomizationSupport.randomizer;
import static org.assertj.core.api.Assertions.assertThat;

import com.naharoo.commons.mapstruct.mapper.basic.nonfinalfields.Address;
import com.naharoo.commons.mapstruct.mapper.basic.nonfinalfields.AddressDto;
import com.naharoo.commons.mapstruct.mapper.basic.nonfinalfields.AddressMapperImpl;
import com.naharoo.commons.mapstruct.mapper.unidirectional.basic.Organization;
import com.naharoo.commons.mapstruct.mapper.unidirectional.basic.OrganizationDto;
import com.naharoo.commons.mapstruct.mapper.unidirectional.basic.OrganizationsMapperImpl;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleMappingFacadeWithManualMappingsTest {

    @Test
    @DisplayName("When unidirectional mapping is configured manually, S -> D mapping should be successful")
    void testUnidirectionalMap() {
        // Given
        final MappingFacade mappingFacade = new SimpleMappingFacade(
            new SimpleMappingsRegistry(Collections.singletonList(new OrganizationsMapperImpl()))
        );
        final Organization organization = randomizer().instance(Organization.class);

        // When
        final OrganizationDto organizationDto = mappingFacade.map(organization, OrganizationDto.class);

        // Then
        assertThat(organizationDto).isNotNull().usingRecursiveComparison().isEqualTo(organization);
    }

    @Test
    @DisplayName("When bidirectional mappings are configured manually, S -> D mapping should be successful")
    void testBidirectionalMapSToD() {
        // Given
        final MappingFacade mappingFacade = new SimpleMappingFacade(
            new SimpleMappingsRegistry(Collections.singletonList(new AddressMapperImpl()))
        );
        final Address address = new Address(
            randomizer().string(),
            randomizer().string(),
            randomizer().int_()
        );

        // When
        final AddressDto addressDto = mappingFacade.map(address, AddressDto.class);

        // Then
        assertThat(addressDto).isNotNull();
        assertThat(addressDto.getCountry()).isEqualTo(address.getCountry());
        assertThat(addressDto.getCity()).isEqualTo(address.getCity());
        assertThat(addressDto.getPostalCode()).isEqualTo(address.getPostalCode());
    }

    @Test
    @DisplayName("When bidirectional mappings are configured manually, D -> S mapping should be successful")
    void testBidirectionalMapDToS() {
        // Given
        final MappingFacade mappingFacade = new SimpleMappingFacade(
            new SimpleMappingsRegistry(Collections.singletonList(new AddressMapperImpl()))
        );
        final AddressDto addressDto = new AddressDto(
            randomizer().string(),
            randomizer().string(),
            randomizer().int_()
        );

        // When
        final Address address = mappingFacade.map(addressDto, Address.class);

        // Then
        assertThat(address).isNotNull();
        assertThat(address.getCountry()).isEqualTo(addressDto.getCountry());
        assertThat(address.getCity()).isEqualTo(addressDto.getCity());
        assertThat(address.getPostalCode()).isEqualTo(addressDto.getPostalCode());
    }
}
