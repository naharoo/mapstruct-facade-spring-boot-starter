package com.naharoo.commons.mapstruct.mapper.advanced.polymorphic;

public class ImageMessage extends Message {

    private final String url;

    public ImageMessage(final Long id, final String url) {
        super(id, MessageType.IMAGE);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
