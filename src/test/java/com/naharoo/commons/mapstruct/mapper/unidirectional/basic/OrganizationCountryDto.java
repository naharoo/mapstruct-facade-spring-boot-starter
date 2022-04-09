package com.naharoo.commons.mapstruct.mapper.unidirectional.basic;

import java.util.Objects;
import java.util.StringJoiner;

public class OrganizationCountryDto {

    private final String name;

    public OrganizationCountryDto(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrganizationCountryDto that = (OrganizationCountryDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrganizationCountryDto.class.getSimpleName() + "[", "]").add("name='" + name + "'").toString();
    }
}
