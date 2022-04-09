package com.naharoo.commons.mapstruct.mapper.advanced.polymorphic;

public abstract class MessageDto {

    private final Long id;
    private final MessageTypeDto type;

    public MessageDto(
        final Long id,
        final MessageTypeDto type
    ) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public MessageTypeDto getType() {
        return type;
    }
}
