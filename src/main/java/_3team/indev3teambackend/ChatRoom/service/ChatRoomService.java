package _3team.indev3teambackend.ChatRoom.service;

import _3team.indev3teambackend.ChatRoom.dto.ChatRoomDto;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import _3team.indev3teambackend.ChatRoom.repository.ChatRoomRepository;
import _3team.indev3teambackend.users.entity.User;
import _3team.indev3teambackend.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    // 💡 Llama Bot의 고정 ID (UserService의 @PostConstruct에서 생성된 ID와 일치해야 함)
    private static final Long LLAMA_BOT_ID = 1L;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }

    //엔티티를 DTO로 변환해주는 Private 메서드
    private ChatRoomDto toDto(ChatRoomEntity chatRoomEntity){
        ChatRoomDto dto = new ChatRoomDto();
        dto.setId(chatRoomEntity.getId());

        // DTO에 userId가 필요하므로 엔티티에서 추출
        dto.setUserId(chatRoomEntity.getUser().getId());

        dto.setCreateAt(chatRoomEntity.getCreateAt());
        return dto;
    }

    /* * ⚠️ [원래의 일반 채팅방 생성 로직]
     * 이 메서드는 Llama 전용 사이트에서는 사용하지 않을 가능성이 높으므로,
     * Llama 전용 메서드로 대체하거나 비활성화하는 것을 고려해야 합니다.
     * 여기서는 충돌 방지를 위해 기존 로직을 제거하고 Llama 전용 로직만 남깁니다.
     */

    // 💡 Llama Bot과 1:1 채팅방만 생성하는 전용 메서드
    // 메서드 이름을 변경하여 기존 createChatRoom(Long)과의 중복을 해결했습니다.
    @Transactional
    public ChatRoomDto createLlamaChatRoom(Long userId) {
        // 1. 사용자(요청자) 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ID " + userId + "인 사용자가 존재하지 않습니다."));

        // 2. Llama Bot 사용자 조회 (ID=1 가정)
        // 실제로 이 llamaBot 객체는 ChatRoomEntity에 직접 연결되지는 않지만,
        // 사용자 ID 유효성 검사 및 Llama 전용 채팅임을 명확히 하기 위해 조회합니다.
        User llamaBot = userRepository.findById(LLAMA_BOT_ID)
                .orElseThrow(() -> new RuntimeException("Llama Bot (ID " + LLAMA_BOT_ID + ") 사용자가 존재하지 않습니다. 시스템 설정을 확인하세요."));

        // 3. 채팅방 엔티티 생성
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();

        // 4. 외래 키 연결: 요청한 사용자를 채팅방 생성자(user)로 설정합니다.
        chatRoomEntity.setUser(user);

        chatRoomEntity = chatRoomRepository.save(chatRoomEntity);
        return toDto(chatRoomEntity);
    }

    /// 특정 사용자 ID로 채팅방 목록을 조회하는 메서드
    public List<ChatRoomDto> getChatRoomsByUserId(Long userId) {
        return chatRoomRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 채팅방 ID로 특정 채팅방을 조회하는 메서드
    public ChatRoomDto getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .map(chatRoom -> toDto(chatRoom))
                .orElse(null);
    }
}