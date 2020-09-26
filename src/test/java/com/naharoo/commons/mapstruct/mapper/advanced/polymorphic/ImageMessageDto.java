package com.naharoo.commons.mapstruct.mapper.advanced.polymorphic;

public class ImageMessageDto extends MessageDto {

    private final String url;

    public ImageMessageDto(final Long id, final String url) {
        super(id, MessageTypeDto.IMAGE);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
