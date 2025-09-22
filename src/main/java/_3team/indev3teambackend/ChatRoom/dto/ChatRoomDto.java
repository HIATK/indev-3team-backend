package _3team.indev3teambackend.ChatRoom.dto;

import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatRoomDto {

    private Long id;
    private Long chatRoomId;
    private String chatRoomName;
    private String content;
    private LocalDateTime createdAt;

    //엔티티 -> DTO
    public static ChatRoomDto fromEntity(ChatRoomEntity entity){
        return ChatRoomDto.builder()
                .id(entity.getId())
                .chatRoomId(entity.getChatRoomId())
                .content(entity.getContent())
                .createdAt(entity.getCreateAt())
                .build();
    }


}
