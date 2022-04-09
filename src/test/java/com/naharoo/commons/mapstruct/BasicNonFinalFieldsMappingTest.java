package com.naharoo.commons.mapstruct;

import com.naharoo.commons.mapstruct.mapper.basic.nonfinalfields.Address;
import com.naharoo.commons.mapstruct.mapper.basic.nonfinalfields.AddressDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan("com.naharoo.commons.mapstruct.mapper.basic.nonfinalfields")
class BasicNonFinalFieldsMappingTest extends AbstractMappingTest {

    @Test
    @DisplayName("When mappings are configured, S -> D mapping should map all fields")
    void testMapSToD() {
        // Given
        final Address address = new Address(
            RANDOM.nextObject(String.class),
            RANDOM.nextObject(String.class),
            RANDOM.nextInt()
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
    @DisplayName("When mappings are configured, D -> S mapping should map all fields")
    void testMapDToS() {
        // Given
        final AddressDto addressDto = new AddressDto(
            RANDOM.nextObject(String.class),
            RANDOM.nextObject(String.class),
            RANDOM.nextInt()
        );

        // When
        final Address address = mappingFacade.map(addressDto, Address.class);

        // Then
        assertThat(address).isNotNull();
        assertThat(address.getCountry()).isEqualTo(addressDto.getCountry());
        assertThat(address.getCity()).isEqualTo(addressDto.getCity());
        assertThat(address.getPostalCode()).isEqualTo(addressDto.getPostalCode());
    }

    @Test
    @DisplayName("When mappings are configured, Set<S> -> Set<D> mapping should map all fields")
    void mapMapSetOfDToSetOfS() {
        // Given
        final Set<Address> addresses = new HashSet<>();
        addresses.add(new Address(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()));
        addresses.add(new Address(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()));
        addresses.add(new Address(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()));

        // When
        final Set<AddressDto> addressDtos = mappingFacade.mapAsSet(addresses, AddressDto.class);

        // Then
        assertThat(addressDtos).isNotNull().isNotEmpty().size().isEqualTo(addresses.size());

        addressDtos.forEach(
            dto -> assertThat(
                addresses.contains(
                    new Address(
                        dto.getCountry(),
                        dto.getCity(),
                        dto.getPostalCode()
                    )
                )
            ).isTrue()
        );
        addresses.forEach(
            address -> assertThat(
                addressDtos.contains(
                    new AddressDto(
                        address.getCountry(),
                        address.getCity(),
                        address.getPostalCode()
                    )
                )
            ).isTrue()
        );
    }

    @Test
    @DisplayName(
        "When mappings are configured, Set<S> -> Set<D> mapping with destination customizations should map all fields and successfully apply customization"
    )
    void mapMapSetOfDToSetOfSWithCustomizer() {
        // Given
        final Set<Address> addresses = new HashSet<>();
        addresses.add(new Address(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()));
        addresses.add(new Address(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()));
        addresses.add(new Address(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()));
        final String country = UUID.randomUUID().toString();

        // When
        final Set<AddressDto> addressDtos = mappingFacade.mapAsSet(
            addresses,
            AddressDto.class,
            destination -> destination.setCountry(country)
        );

        // Then
        assertThat(addressDtos).isNotNull().isNotEmpty().size().isEqualTo(addresses.size());

        addresses.forEach(
            address -> assertThat(
                addressDtos.contains(
                    new AddressDto(
                        country,
                        address.getCity(),
                        address.getPostalCode()
                    )
                )
            ).isTrue()
        );
    }

    @Test
    @DisplayName("When mappings are configured, List<D> -> List<S> mapping should map all fields")
    void testMapListOfDToListOfS() {
        // Given
        final List<AddressDto> addressDtos = Arrays.asList(
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()),
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()),
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt())
        );

        // When
        final List<Address> addresses = mappingFacade.mapAsList(addressDtos, Address.class);

        // Then
        assertThat(addresses).isNotNull().isNotEmpty().size().isEqualTo(addressDtos.size());

        for (int i = 0; i < addresses.size(); i++) {
            final Address address = addresses.get(i);
            final AddressDto addressDto = addressDtos.get(i);
            assertThat(addressDto.getCountry()).isEqualTo(address.getCountry());
            assertThat(addressDto.getCity()).isEqualTo(address.getCity());
            assertThat(addressDto.getPostalCode()).isEqualTo(address.getPostalCode());
        }
    }

    @Test
    @DisplayName(
        "When mappings are configured, List<D> -> List<S> mapping with destination customizations should map all fields and successfully apply customization"
    )
    void testMapListOfDToListOfSWithCustomization() {
        // Given
        final List<AddressDto> addressDtos = Arrays.asList(
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()),
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()),
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt())
        );
        final String country = UUID.randomUUID().toString();

        // When
        final List<Address> addresses = mappingFacade.mapAsList(
            addressDtos,
            Address.class,
            destination -> destination.setCountry(country)
        );

        // Then
        assertThat(addresses).isNotNull().isNotEmpty().size().isEqualTo(addressDtos.size());

        for (int i = 0; i < addresses.size(); i++) {
            final Address address = addresses.get(i);
            final AddressDto addressDto = addressDtos.get(i);
            assertThat(country).isEqualTo(address.getCountry());
            assertThat(addressDto.getCity()).isEqualTo(address.getCity());
            assertThat(addressDto.getPostalCode()).isEqualTo(address.getPostalCode());
        }
    }

    @Test
    @DisplayName("When mappings are configured, Collection<D> -> S[] mapping should map all fields")
    void testMapCollectionOfDToArrayOfS() {
        // Given
        final List<AddressDto> addressDtos = Arrays.asList(
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()),
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()),
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt())
        );

        // When
        final Address[] addresses = mappingFacade.mapAsArray(addressDtos, Address.class);

        // Then
        assertThat(addresses).isNotNull().isNotEmpty().hasSize(addressDtos.size());

        for (int i = 0; i < addresses.length; i++) {
            final Address address = addresses[i];
            final AddressDto addressDto = addressDtos.get(i);
            assertThat(addressDto.getCountry()).isEqualTo(address.getCountry());
            assertThat(addressDto.getCity()).isEqualTo(address.getCity());
            assertThat(addressDto.getPostalCode()).isEqualTo(address.getPostalCode());
        }
    }

    @Test
    @DisplayName("When mappings are configured, D[] -> S[] mapping should map all fields")
    void testMapArrayOfDToArrayOfS() {
        // Given
        final AddressDto[] addressDtos = {
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()),
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt()),
            new AddressDto(RANDOM.nextObject(String.class), RANDOM.nextObject(String.class), RANDOM.nextInt())
        };

        // When
        final Address[] addresses = mappingFacade.mapAsArray(addressDtos, Address.class);

        // Then
        assertThat(addresses).isNotNull().isNotEmpty().hasSize(addressDtos.length);

        for (int i = 0; i < addresses.length; i++) {
            final Address address = addresses[i];
            final AddressDto addressDto = addressDtos[i];
            assertThat(addressDto.getCountry()).isEqualTo(address.getCountry());
            assertThat(addressDto.getCity()).isEqualTo(address.getCity());
            assertThat(addressDto.getPostalCode()).isEqualTo(address.getPostalCode());
        }
    }

    @Test
    @DisplayName(
        "When mappings are configured, S -> D mapping with destination customizations should map all fields and successfully apply customization"
    )
    void testMapSToDWithDestinationCustomization() {
        // Given
        final Address address = new Address(
            RANDOM.nextObject(String.class),
            RANDOM.nextObject(String.class),
            RANDOM.nextInt()
        );
        final String customizedCountry = UUID.randomUUID().toString();

        // When
        final AddressDto addressDto = mappingFacade.map(
            address,
            AddressDto.class,
            dto -> dto.setCountry(customizedCountry)
        );

        // Then
        assertThat(addressDto).isNotNull();
        assertThat(addressDto.getCountry()).isEqualTo(customizedCountry);
        assertThat(addressDto.getCity()).isEqualTo(address.getCity());
        assertThat(addressDto.getPostalCode()).isEqualTo(address.getPostalCode());
    }
}
