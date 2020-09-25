package com.naharoo.commons.mapstruct.mapper.basic.finalfields.car;

public final class CarDto {

    private final Long id;
    private final String make;
    private final CarTypeDto type;

    public CarDto(final Long id, final String make, final CarTypeDto type) {
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

    public CarTypeDto getType() {
        return type;
    }
}
