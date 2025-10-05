package _3team.indev3teambackend.ChatMessage.service;

import _3team.indev3teambackend.ChatMessage.dto.ChatMessagesDto;
import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import _3team.indev3teambackend.ChatMessage.repository.ChatMessagesRepository;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import _3team.indev3teambackend.ChatRoom.repository.ChatRoomRepository;
import _3team.indev3teambackend.ChatRoom.service.ChatRoomService;
import _3team.indev3teambackend.Llama.service.AILlamaService;
import _3team.indev3teambackend.users.entity.User;
import _3team.indev3teambackend.users.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 💡 새로 추가된 Llama 서비스 주입
    private final AILlamaService aiLlamaService;

    //채팅 추가하는 메서드 (AI 응답 로직 추가)
    @Transactional
    public ChatMessagesDto createChat(ChatMessagesDto chatMessagesDto) {

        // 1. FK 객체 조회 (기존 로직)
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatMessagesDto.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다: " + chatMessagesDto.getChatRoomId()));

        User user = userRepository.findById(chatMessagesDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + chatMessagesDto.getUserId()));

        // 2. 사용자 메시지 Entity 생성 및 저장 (기존 로직)
        ChatMessagesEntity chatMessagesEntity = new ChatMessagesEntity();
        chatMessagesEntity.setChatRoom(chatRoom);
        chatMessagesEntity.setUser(user);
        chatMessagesEntity.setContent(chatMessagesDto.getContent());
        chatMessagesEntity.setCreateAt(LocalDateTime.now());
        ChatMessagesEntity savedEntity = chatMessagesRepository.save(chatMessagesEntity);

        // 3. 💡 Llama Bot 응답 생성 및 저장 (가장 중요한 부분)
        // 비동기(@Async)로 처리하여 사용자에게 즉시 응답을 반환할 수 있도록 하는 것이 좋습니다.
        // @Async 사용 시, Spring Boot Application에 @EnableAsync 추가 필요.
        // 여기서는 간단히 동기 호출로 구현합니다.
        aiLlamaService.getAndSaveLlamaResponse(chatMessagesDto, chatRoom);

        // 4. 저장된 사용자 메시지 DTO 반환
        return ChatMessagesDto.fromEntity(savedEntity);
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