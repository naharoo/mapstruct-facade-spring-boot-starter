package com.naharoo.commons.mapstruct.mapper.unidirectional.basic;

import java.util.Objects;
import java.util.StringJoiner;

public class OrganizationCountry {

    private final String name;

    public OrganizationCountry(final String name) {
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
        OrganizationCountry that = (OrganizationCountry) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrganizationCountry.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
