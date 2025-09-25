package _3team.indev3teambackend.ChatMessage.service;

import _3team.indev3teambackend.ChatMessage.dto.ChatMessagesDto;
import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import _3team.indev3teambackend.ChatMessage.repository.ChatMessagesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessagesService {

    private final ChatMessagesRepository chatMessagesRepository;

    //채팅 추가하는 메서드
    @Transactional
    public ChatMessagesDto createChat(ChatMessagesDto chatMessagesDto) {
        ChatMessagesEntity chatMessagesEntity = new ChatMessagesEntity();
        chatMessagesEntity.setChatMessagesId(chatMessagesDto.getChatMessagesId());
        chatMessagesEntity.setContent(chatMessagesDto.getContent());
        chatMessagesEntity.setCreateAt(LocalDateTime.now());
        ChatMessagesEntity saveEntity = chatMessagesRepository.save(chatMessagesEntity);
        return ChatMessagesDto.fromEntity(saveEntity);
    }

    //특정 채팅방의 모든 메세지 조회
    @Transactional
    public List<ChatMessagesDto> getChatsByChatMessagesId(Long chatRoomId) {
        List<ChatMessagesEntity> entities = chatMessagesRepository.findByChatMessagesIdOrderByCreateAtAsc(chatRoomId);
        return entities.stream()
                .map(ChatMessagesDto::fromEntity)
                .collect(Collectors.toList());
    }

    //메세지 수정하기
    @Transactional
    public ChatMessagesDto updateChat(Long id, ChatMessagesDto chatMessagesDto){
        ChatMessagesEntity entity = chatMessagesRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("메세지를 조회할 수 없습니다 : "+id));
        entity.setContent((chatMessagesDto.getContent()));
        ChatMessagesEntity updatedEntity = chatMessagesRepository.save(entity);
        return ChatMessagesDto.fromEntity(updatedEntity);
    }

    //메세지 삭제하기
    @Transactional
    public void deleteChat(Long id) {
        if(!chatMessagesRepository.existsById(id)){
            throw new IllegalArgumentException("메세지 조회 불가능 ㅋ : "+id );
        }
        chatMessagesRepository.deleteById(id);
    }
}
