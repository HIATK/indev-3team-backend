package _3team.indev3teambackend.ChatRoom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "chat_room_id",updatable = true)
    private Long chatRoomId;

    @Column(name = "content",updatable = true)
    private String content;

    @Column(name = "create_at",updatable = false)
    private LocalDateTime createAt;
}
