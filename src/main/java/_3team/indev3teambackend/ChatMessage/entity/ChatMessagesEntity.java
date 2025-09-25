package _3team.indev3teambackend.ChatMessage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ChatMessagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "chat_messages_id",updatable = true)
    private Long chatMessagesId;

    @Column(name = "content",updatable = true)
    private String content;

    @Column(name = "create_at",updatable = false)
    private LocalDateTime createAt;
}
