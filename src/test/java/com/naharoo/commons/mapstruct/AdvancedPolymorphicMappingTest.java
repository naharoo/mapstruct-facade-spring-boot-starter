package com.naharoo.commons.mapstruct;

import com.naharoo.commons.mapstruct.mapper.advanced.polymorphic.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan("com.naharoo.commons.mapstruct.mapper.advanced.polymorphic")
public class AdvancedPolymorphicMappingTest extends AbstractMappingTest {

    @Test
    @DisplayName("When polymorphic mappings are set, all subtypes should be mapped S -> D as expected")
    void testMapListOfSToListOfD() {
        // Given
        final TextMessage textMessage = new TextMessage(RANDOM.nextLong(), RANDOM.nextObject(String.class));
        final ImageMessage imageMessage = new ImageMessage(RANDOM.nextLong(), RANDOM.nextObject(String.class));
        final List<Message> messages = Arrays.asList(textMessage, imageMessage);

        // When
        final List<MessageDto> messageDtos = mappingFacade.mapAsList(messages, MessageDto.class);

        // Then
        assertThat(messageDtos).isNotNull().isNotEmpty().size().isEqualTo(messages.size());

        assertThat(messageDtos.get(0)).isInstanceOf(TextMessageDto.class);
        assertThat(messageDtos.get(1)).isInstanceOf(ImageMessageDto.class);

        final TextMessageDto textMessageDto = (TextMessageDto) messageDtos.get(0);
        assertThat(textMessageDto.getId()).isEqualTo(textMessage.getId());
        assertThat(textMessageDto.getText()).isEqualTo(textMessage.getText());

        final ImageMessageDto imageMessageDto = (ImageMessageDto) messageDtos.get(1);
        assertThat(imageMessageDto.getId()).isEqualTo(imageMessage.getId());
        assertThat(imageMessageDto.getUrl()).isEqualTo(imageMessage.getUrl());
    }

    @Test
    @DisplayName("When polymorphic mappings are set, all subtypes should be able to be mapped to " +
            "concrete corresponding destinations")
    void testMapDToS() {
        // Given
        final TextMessageDto textMessageDto = new TextMessageDto(RANDOM.nextLong(), RANDOM.nextObject(String.class));
        final ImageMessageDto imageMessageDto = new ImageMessageDto(RANDOM.nextLong(), RANDOM.nextObject(String.class));

        // When
        final TextMessage textMessage = mappingFacade.map(textMessageDto, TextMessage.class);
        final ImageMessage imageMessage = mappingFacade.map(imageMessageDto, ImageMessage.class);

        // Then
        assertThat(textMessage).isNotNull();
        assertThat(textMessage.getId()).isEqualTo(textMessageDto.getId());
        assertThat(textMessage.getText()).isEqualTo(textMessageDto.getText());

        assertThat(imageMessage).isNotNull();
        assertThat(imageMessage.getId()).isEqualTo(imageMessageDto.getId());
        assertThat(imageMessage.getUrl()).isEqualTo(imageMessageDto.getUrl());
    }

    @Test
    @DisplayName("When polymorphic mappings are set and concrete-to-abstract mappings are cached, " +
            "all subtypes should be able to be mapped to concrete corresponding destinations")
    void testMapSToDAfterCaching() {
        // Given
        final TextMessage textMessage = new TextMessage(RANDOM.nextLong(), RANDOM.nextObject(String.class));
        final ImageMessage imageMessage = new ImageMessage(RANDOM.nextLong(), RANDOM.nextObject(String.class));
        final Set<Message> messages = new HashSet<>();
        messages.add(textMessage);
        messages.add(imageMessage);
        mappingFacade.mapAsList(messages, MessageDto.class);

        // When
        final TextMessageDto textMessageDto = mappingFacade.map(textMessage, TextMessageDto.class);
        final ImageMessageDto imageMessageDto = mappingFacade.map(imageMessage, ImageMessageDto.class);

        // Then
        assertThat(textMessageDto).isNotNull();
        assertThat(textMessageDto.getId()).isEqualTo(textMessage.getId());
        assertThat(textMessageDto.getText()).isEqualTo(textMessage.getText());

        assertThat(imageMessageDto).isNotNull();
        assertThat(imageMessageDto.getId()).isEqualTo(imageMessage.getId());
        assertThat(imageMessageDto.getUrl()).isEqualTo(imageMessage.getUrl());
    }
}
