package com.naharoo.commons.mapstruct.mapper.proxies.sourceclass;

public class Laptop {

    private final String brand;
    private final String name;

    public Laptop(final String brand, final String name) {
        this.brand = brand;
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }
}
