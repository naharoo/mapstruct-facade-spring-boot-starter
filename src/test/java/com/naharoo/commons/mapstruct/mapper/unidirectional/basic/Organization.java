package com.naharoo.commons.mapstruct.mapper.unidirectional.basic;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class Organization {

    private final UUID uuid;
    private final String name;
    private final OrganizationCountry country;
    private final OrganizationType type;

    public Organization(
            final UUID uuid,
            final String name,
            final OrganizationCountry country,
            final OrganizationType type
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

    public OrganizationCountry getCountry() {
        return country;
    }

    public OrganizationType getType() {
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
        Organization that = (Organization) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Organization.class.getSimpleName() + "[", "]")
                .add("uuid=" + uuid)
                .add("name='" + name + "'")
                .add("country=" + country)
                .add("type=" + type)
                .toString();
    }
}
