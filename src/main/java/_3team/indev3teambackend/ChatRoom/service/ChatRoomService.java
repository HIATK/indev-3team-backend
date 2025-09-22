package _3team.indev3teambackend.ChatRoom.service;

import _3team.indev3teambackend.ChatRoom.dto.ChatRoomDto;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import _3team.indev3teambackend.ChatRoom.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    //채팅 추가하는 메서드
    @Transactional
    public ChatRoomDto createChat(ChatRoomDto chatRoomDto) {
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setChatRoomId(chatRoomDto.getChatRoomId());
        chatRoomEntity.setContent(chatRoomDto.getContent());
        chatRoomEntity.setCreateAt(LocalDateTime.now());
        ChatRoomEntity saveEntity = chatRoomRepository.save(chatRoomEntity);
        return ChatRoomDto.fromEntity(saveEntity);
    }

    //특정 채팅방의 모든 메세지 조회
    @Transactional
    public List<ChatRoomDto> getChatsByChatRoomId(Long chatRoomId) {
        List<ChatRoomEntity> entities = chatRoomRepository.findByChatRoomIdOrderByCreateAtAsc(chatRoomId);
        return entities.stream()
                .map(ChatRoomDto::fromEntity)
                .collect(Collectors.toList());
    }

    //메세지 수정하기
    @Transactional
    public ChatRoomDto updateChat(Long id, ChatRoomDto chatRoomDto){
        ChatRoomEntity entity = chatRoomRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("메세지를 조회할 수 없습니다 : "+id));
        entity.setContent((chatRoomDto.getContent()));
        ChatRoomEntity updatedEntity = chatRoomRepository.save(entity);
        return ChatRoomDto.fromEntity(updatedEntity);
    }

    //메세지 삭제하기
    @Transactional
    public void deleteChat(Long id) {
        if(!chatRoomRepository.existsById(id)){
            throw new IllegalArgumentException("메세지 조회 불가능 ㅋ : "+id );
        }
        chatRoomRepository.deleteById(id);
    }
}
