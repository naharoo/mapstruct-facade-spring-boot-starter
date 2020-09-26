package com.naharoo.commons.mapstruct.mapper.basic.nonfinalfields;

import java.util.Objects;

public class AddressDto {

    private String country;
    private String city;
    private int postalCode;

    public AddressDto() {
        this(null, null, 0);
    }

    public AddressDto(final String country, final String city, final int postalCode) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final int postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressDto that = (AddressDto) o;
        return postalCode == that.postalCode &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, postalCode);
    }
}
