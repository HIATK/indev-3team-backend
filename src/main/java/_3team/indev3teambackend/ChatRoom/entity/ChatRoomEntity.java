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
@Table(name = "chat_room")
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;


    @Column(name = "user_id",updatable = false)
    private Long userId;

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createAt;

    @PrePersist
    public void OnPrePersist() {
        this.createAt = LocalDateTime.now();
    }
}
