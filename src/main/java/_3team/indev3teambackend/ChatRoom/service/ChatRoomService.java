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

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository; // ✅ 2. 의존성 주입 완료
    }

    //엔티티를 DTO로 변환해주는 Private 메서드
    private ChatRoomDto toDto(ChatRoomEntity chatRoomEntity){
        ChatRoomDto dto = new ChatRoomDto();
        dto.setId(chatRoomEntity.getId());

        // 🚨 DTO에 userId가 필요하다면 아래 로직을 사용해야 합니다.
        dto.setUserId(chatRoomEntity.getUser().getId());

        dto.setCreateAt(chatRoomEntity.getCreateAt());
        return dto;
    }

    //새로운 채팅방 생성하는 메서드
    @Transactional
    public ChatRoomDto createChatRoom(Long userId){
        // ✅ 3. userId로 DummyEntity(User) 객체를 조회합니다.
        // ID가 없을 경우 RuntimeException을 발생시켜 user_id = null 저장을 방지합니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ID " + userId + "인 사용자가 존재하지 않습니다. 채팅방을 생성할 수 없습니다."));

        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();

        // ✅ 4. 외래 키 연결: ID가 아닌 User 객체 자체를 설정합니다.
        chatRoomEntity.setUser(user);

        chatRoomEntity = chatRoomRepository.save(chatRoomEntity);
        return toDto(chatRoomEntity);
    }

    /// 특정 사용자 ID로 채팅방 목록을 조회하는 메서드
    public List<ChatRoomDto> getChatRoomsByUserId(Long userId) {
        // ... (이 부분은 repository 쿼리가 유효하다면 그대로 사용 가능)
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