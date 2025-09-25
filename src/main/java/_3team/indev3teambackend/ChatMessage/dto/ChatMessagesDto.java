package _3team.indev3teambackend.ChatMessage.dto;

import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatMessagesDto {

    private Long id;
    private Long chatMessagesId;
    private String chatMessagesName;
    private String content;
    private LocalDateTime createdAt;

    //엔티티 -> DTO
    public static ChatMessagesDto fromEntity(ChatMessagesEntity entity){
        return ChatMessagesDto.builder()
                .id(entity.getId())
                .chatMessagesId(entity.getChatMessagesId())
                .content(entity.getContent())
                .createdAt(entity.getCreateAt())
                .build();
    }


}
