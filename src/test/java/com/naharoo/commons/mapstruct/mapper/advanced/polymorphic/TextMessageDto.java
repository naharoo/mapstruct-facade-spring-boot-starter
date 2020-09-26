package com.naharoo.commons.mapstruct.mapper.advanced.polymorphic;

public class TextMessageDto extends MessageDto {

    private final String text;

    public TextMessageDto(final Long id, final String text) {
        super(id, MessageTypeDto.TEXT);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
