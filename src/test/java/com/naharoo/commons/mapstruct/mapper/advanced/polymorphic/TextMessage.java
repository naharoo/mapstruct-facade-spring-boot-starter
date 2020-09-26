package com.naharoo.commons.mapstruct.mapper.advanced.polymorphic;

public class TextMessage extends Message {

    private final String text;

    public TextMessage(final Long id, final String text) {
        super(id, MessageType.TEXT);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
