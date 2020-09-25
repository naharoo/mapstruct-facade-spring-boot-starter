package com.naharoo.commons.mapstruct.mapper.basic.finalfields.car;

public final class Car {

    private final Long id;
    private final String make;
    private final CarType type;

    public Car(final Long id, final String make, final CarType type) {
        this.id = id;
        this.make = make;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public CarType getType() {
        return type;
    }
}
