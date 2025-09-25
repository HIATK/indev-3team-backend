package _3team.indev3teambackend.ChatMessage.service;

import _3team.indev3teambackend.ChatMessage.dto.ChatMessagesDto;
import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import _3team.indev3teambackend.ChatMessage.repository.ChatMessagesRepository;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import _3team.indev3teambackend.ChatRoom.repository.ChatRoomRepository;
import _3team.indev3teambackend.ChatRoom.service.ChatRoomService;
import _3team.indev3teambackend.DummyUser.EntityDummy.DummyEntity;
import _3team.indev3teambackend.DummyUser.repository.DummyUserRepository;
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
    private final ChatRoomService chatRoomService;
    private final DummyUserRepository dummyUserRepository;
    private final ChatRoomRepository chatRoomRepository;

    //채팅 추가하는 메서드 (이 부분은 FK 연결 로직이 이미 올바릅니다.)
    @Transactional
    public ChatMessagesDto createChat(ChatMessagesDto chatMessagesDto) {
        // ... (createChat 로직은 생략. 올바름)
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatMessagesDto.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다: " + chatMessagesDto.getChatRoomId()));

        DummyEntity user = dummyUserRepository.findById(chatMessagesDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + chatMessagesDto.getUserId()));

        ChatMessagesEntity chatMessagesEntity = new ChatMessagesEntity();

        chatMessagesEntity.setChatRoom(chatRoom);
        chatMessagesEntity.setUser(user);
        chatMessagesEntity.setContent(chatMessagesDto.getContent());
        chatMessagesEntity.setCreateAt(LocalDateTime.now());

        ChatMessagesEntity saveEntity = chatMessagesRepository.save(chatMessagesEntity);
        return ChatMessagesDto.fromEntity(saveEntity);
    }

    // ✅ 수정된 메서드: 특정 채팅방의 모든 메세지 조회
    @Transactional
    public List<ChatMessagesDto> getChatsByChatRoomId(Long chatRoomId) { // 👈 메서드 이름 변경

        // ✅ Repository 호출 수정: chatRoom 객체의 ID를 참조하도록 findByChatRoom_Id 사용
        List<ChatMessagesEntity> entities = chatMessagesRepository.findByChatRoomIdOrderByCreateAtAsc(chatRoomId);

        return entities.stream()
                .map(ChatMessagesDto::fromEntity)
                .collect(Collectors.toList());
    }

    //메세지 수정하기 (올바름)
    @Transactional
    public ChatMessagesDto updateChat(Long id, ChatMessagesDto chatMessagesDto){
        ChatMessagesEntity entity = chatMessagesRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("메세지를 조회할 수 없습니다 : "+id));
        entity.setContent((chatMessagesDto.getContent()));
        ChatMessagesEntity updatedEntity = chatMessagesRepository.save(entity);
        return ChatMessagesDto.fromEntity(updatedEntity);
    }

    //메세지 삭제하기 (올바름)
    @Transactional
    public void deleteChat(Long id) {
        if(!chatMessagesRepository.existsById(id)){
            throw new IllegalArgumentException("메세지 조회 불가능 ㅋ : "+id );
        }
        chatMessagesRepository.deleteById(id);
    }
}