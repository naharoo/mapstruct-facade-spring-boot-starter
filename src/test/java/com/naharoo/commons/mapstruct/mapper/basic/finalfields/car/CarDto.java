package com.naharoo.commons.mapstruct.mapper.basic.finalfields.car;

import java.math.BigDecimal;

public final class CarDto {

    private final Long id;
    private final String make;
    private final CarTypeDto type;
    private final int year;
    private final BigDecimal price;
    private final double weight;

    public CarDto(
            final Long id,
            final String make,
            final CarTypeDto type,
            final int year,
            final BigDecimal price,
            final double weight
    ) {
        this.id = id;
        this.make = make;
        this.type = type;
        this.year = year;
        this.price = price;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public CarTypeDto getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }
}
