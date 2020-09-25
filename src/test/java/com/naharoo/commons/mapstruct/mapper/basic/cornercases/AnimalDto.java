package com.naharoo.commons.mapstruct.mapper.basic.cornercases;

public class AnimalDto {

    private Long id;
    private String name;

    public AnimalDto() {
        this(null, null);
    }

    public AnimalDto(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
