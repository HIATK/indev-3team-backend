package _3team.indev3teambackend.users.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 엔티티
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "providerId", nullable = false, unique = true)
    private String providerId;

    @Column(name = "providerId_email", unique = true)
    private String providerIdEmail;

    private String name;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
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


    public User(String providerId, String providerIdEmail, String name, String profileImageUrl) {
        this.providerId = providerId;
        this.providerIdEmail = providerIdEmail;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public void changeName(String name) { 
        this.name = name; 
    }

    public void changeProfileImageUrl(String profileImageUrl) { 
        this.profileImageUrl = profileImageUrl; 
    }

}
