package _3team.indev3teambackend.DummyUser.EntityDummy;

import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data // Lombok: Getter, Setter 등을 자동 생성
public class DummyEntity { // 클래스 이름을 'Users' 대신 단수형 'User'로 쓰는 것이 일반적입니다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "provider_id", unique = true, nullable = false, length = 255)
    private String providerId; // 카카오 고유 ID

    @Column(name = "provider_id_email", unique = true, length = 255)
    private String providerIdEmail; // 카카오 이메일

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ----------------------------------
    // 외래 키(FK) 연결 - 관계 설정
    // ----------------------------------

    // 1. User와 ChatRooms 간의 1:N 관계 (사용자가 생성한 채팅방)
    // "user" 필드는 ChatRoom 엔티티에서 User를 참조하는 필드 이름입니다.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomEntity> chatRooms = new ArrayList<>();

    // 2. User와 ChatMessages 간의 1:N 관계 (사용자가 보낸 메시지)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessagesEntity> chatMessages = new ArrayList<>();


    // 생성 및 업데이트 시각 자동 설정
    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}