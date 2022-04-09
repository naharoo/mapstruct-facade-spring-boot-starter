package com.naharoo.commons.mapstruct.mapper.advanced.polymorphic;

public abstract class Message {

    private final Long id;
    private final MessageType type;

    public Message(
        final Long id,
        final MessageType type
    ) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public MessageType getType() {
        return type;
    }
}
