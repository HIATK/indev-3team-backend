package _3team.indev3teambackend.ChatMessage.controller;

import _3team.indev3teambackend.ChatMessage.dto.ChatMessagesDto;
import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import _3team.indev3teambackend.ChatMessage.repository.ChatMessagesRepository;
import _3team.indev3teambackend.ChatMessage.service.ChatMessagesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatMessagesServiceTest {
    @Mock
    private ChatMessagesRepository chatMessagesRepository;

    @InjectMocks
    private ChatMessagesService chatMessagesService;

    private ChatMessagesEntity chatMessagesEntity1;
    private ChatMessagesEntity chatMessagesEntity2;

    // 테스트에 사용될 메시지 내용을 상수로 정의
    private final String MESSAGE_1 = "첫 번째 메시지";
    private final String MESSAGE_2 = "두 번째 메시지";

    @BeforeEach
    void setUp() {
        chatMessagesEntity1 = new ChatMessagesEntity();
        chatMessagesEntity1.setId(1L);
        chatMessagesEntity1.setChatMessagesId(1L);
        chatMessagesEntity1.setContent(MESSAGE_1);
        chatMessagesEntity1.setCreateAt(LocalDateTime.now());

        chatMessagesEntity2 = new ChatMessagesEntity();
        chatMessagesEntity2.setId(2L);
        chatMessagesEntity2.setChatMessagesId(1L);
        chatMessagesEntity2.setContent(MESSAGE_2);
        chatMessagesEntity2.setCreateAt(LocalDateTime.now().plusSeconds(10));
    }

    @Test
    @DisplayName("메시지 생성 테스트")
    void createChatTest() {
        // given
        ChatMessagesDto chatMessagesDto = ChatMessagesDto.fromEntity(chatMessagesEntity1);
        when(chatMessagesRepository.save(any(ChatMessagesEntity.class))).thenReturn(chatMessagesEntity1);

        // when
        ChatMessagesDto createdDto = chatMessagesService.createChat(chatMessagesDto);

        // then
        assertNotNull(createdDto);
        assertEquals(1L, createdDto.getChatMessagesId());
        assertEquals(MESSAGE_1, createdDto.getContent()); // 상수를 사용하여 검증
        verify(chatMessagesRepository, times(1)).save(any(ChatMessagesEntity.class));
    }

    @Test
    @DisplayName("특정 채팅방 메시지 목록 조회 테스트")
    void getChatsByChatMessagesIdTest() {
        // given
        when(chatMessagesRepository.findByChatMessagesIdOrderByCreateAtAsc(1L))
                .thenReturn(Arrays.asList(chatMessagesEntity1, chatMessagesEntity2));

        // when
        List<ChatMessagesDto> chatList = chatMessagesService.getChatsByChatMessagesId(1L);

        // then
        assertNotNull(chatList);
        assertEquals(2, chatList.size());
        assertEquals(MESSAGE_1, chatList.get(0).getContent()); // 상수를 사용하여 검증
        assertEquals(MESSAGE_2, chatList.get(1).getContent());
        verify(chatMessagesRepository, times(1)).findByChatMessagesIdOrderByCreateAtAsc(1L);
    }

    @Test
    @DisplayName("메시지 수정 테스트")
    void updateChatTest() {
        // given
        ChatMessagesDto updatedDto = ChatMessagesDto.fromEntity(chatMessagesEntity1);
        updatedDto.setContent("수정된 메시지");
        when(chatMessagesRepository.findById(1L)).thenReturn(Optional.of(chatMessagesEntity1));
        when(chatMessagesRepository.save(any(ChatMessagesEntity.class))).thenReturn(chatMessagesEntity1);

        // when
        ChatMessagesDto resultDto = chatMessagesService.updateChat(1L, updatedDto);

        // then
        assertNotNull(resultDto);
        assertEquals("수정된 메시지", resultDto.getContent());
        verify(chatMessagesRepository, times(1)).findById(1L);
        verify(chatMessagesRepository, times(1)).save(any(ChatMessagesEntity.class));
    }

    @Test
    @DisplayName("메시지 삭제 테스트")
    void deleteChatTest() {
        // given
        when(chatMessagesRepository.existsById(1L)).thenReturn(true);
        doNothing().when(chatMessagesRepository).deleteById(1L);

        // when
        chatMessagesService.deleteChat(1L);

        // then
        verify(chatMessagesRepository, times(1)).existsById(1L);
        verify(chatMessagesRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 메시지 삭제 시 예외 발생 테스트")
    void deleteChatNotFoundTest() {
        // given
        when(chatMessagesRepository.existsById(99L)).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> chatMessagesService.deleteChat(99L));
        verify(chatMessagesRepository, times(1)).existsById(99L);
        verify(chatMessagesRepository, never()).deleteById(anyLong());
    }
}
