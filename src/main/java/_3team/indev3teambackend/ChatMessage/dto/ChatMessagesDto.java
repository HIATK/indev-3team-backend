package _3team.indev3teambackend.ChatMessage.dto;

import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessagesDto {

    private Long id;
    private Long chatMessagesId;
    private String chatMessagesName;
    private String content;
    private LocalDateTime createdAt;

    //외래키를 위해 작성
    private Long chatRoomId;
    private Long userId;

    //엔티티 -> DTO
    public static ChatMessagesDto fromEntity(ChatMessagesEntity entity){
        // 🚨 3. toDto 변환 시 FK 정보를 엔티티 객체에서 추출하여 DTO에 설정
        Long chatRoomId = null;
        if (entity.getChatRoom() != null) {
            chatRoomId = entity.getChatRoom().getId();
        }

        Long userId = null;
        if (entity.getUser() != null) {
            userId = entity.getUser().getId();
        }

        return ChatMessagesDto.builder()
                .id(entity.getId())
                .chatRoomId(chatRoomId) // ✅ 추출한 chatRoomId 설정
                .userId(userId)         // ✅ 추출한 userId 설정
                .content(entity.getContent())
                .createdAt(entity.getCreateAt())
                .build();
    }


}
