package _3team.indev3teambackend.ChatRoom.service;

import _3team.indev3teambackend.ChatRoom.dto.ChatRoomDto;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import _3team.indev3teambackend.ChatRoom.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    //엔티티를 DTO로 변환해주는 Private 메서드
    private ChatRoomDto toDto(ChatRoomEntity chatRoomEntity){
        ChatRoomDto dto = new ChatRoomDto();
        dto.setId(chatRoomEntity.getId());
        dto.setUserId(chatRoomEntity.getUserId());
        dto.setCreateAt(chatRoomEntity.getCreateAt());
        return dto;
    }
    //새로운 채팅방 생성하는 메서드
    @Transactional
    public ChatRoomDto createChatRoom(Long userId){
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setUserId(userId);
        chatRoomEntity = chatRoomRepository.save(chatRoomEntity);
        return toDto(chatRoomEntity);//
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
                .map(chatRoom -> toDto(chatRoom)) // <--- 이 부분을 이렇게 수정하세요.
                .orElse(null); // 채팅방이 없으면 null 반환
    }

}
