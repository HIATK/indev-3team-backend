package _3team.indev3teambackend.ChatRoom.entity;

import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import _3team.indev3teambackend.DummyUser.EntityDummy.DummyEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


    ///
    /// 더미User와 1대1 관계로 연(ChatRoom을 생성한 사용자)
    ///
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private DummyEntity user; //dummyEntity 객체로 매핑

//    @Column(name = "user_id",updatable = false)
//    private Long userId;

    // ----------------------------------
    // 2. ChatMessages와의 1:N 관계
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessagesEntity> messages = new ArrayList<>();
    //

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createAt;

    @PrePersist
    public void OnPrePersist() {
        this.createAt = LocalDateTime.now();
    }
}
