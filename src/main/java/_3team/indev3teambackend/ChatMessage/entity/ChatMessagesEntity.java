package _3team.indev3teambackend.ChatMessage.entity;

import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import _3team.indev3teambackend.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "chat_messages")
public class ChatMessagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    // ----------------------------------
    // 1. ChatRoom과의 N:1 관계 (FK: chat_room_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false, updatable = false)
    private ChatRoomEntity chatRoom;

    // ----------------------------------
    // 2. User와의 N:1 관계 (FK: user_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;


//    @Column(name = "chat_messages_id",updatable = true)
//    private Long chatMessagesId;

    @Lob
    @Column(name = "content",updatable = true,columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createAt;
}
