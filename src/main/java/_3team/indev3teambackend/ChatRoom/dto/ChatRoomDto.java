package _3team.indev3teambackend.ChatRoom.dto;

import _3team.indev3teambackend.ChatMessage.dto.ChatMessagesDto;
import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class ChatRoomDto {

    private Long id;
    private Long userId;
    private LocalDateTime createAt;




}
