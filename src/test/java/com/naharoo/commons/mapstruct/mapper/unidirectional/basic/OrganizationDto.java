package com.naharoo.commons.mapstruct.mapper.unidirectional.basic;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class OrganizationDto {

    private final UUID uuid;
    private final String name;
    private final OrganizationCountryDto country;
    private final OrganizationTypeDto type;

    public OrganizationDto(
        final UUID uuid,
        final String name,
        final OrganizationCountryDto country,
        final OrganizationTypeDto type
    ) {
        this.uuid = uuid;
        this.name = name;
        this.country = country;
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public OrganizationCountryDto getCountry() {
        return country;
    }

    public OrganizationTypeDto getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrganizationDto that = (OrganizationDto) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrganizationDto.class.getSimpleName() + "[", "]").add("uuid=" + uuid).add("name='" + name + "'").add(
            "country=" + country
        ).add("type=" + type).toString();
    }
}
