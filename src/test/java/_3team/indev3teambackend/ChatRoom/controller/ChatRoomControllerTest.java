package _3team.indev3teambackend.ChatRoom.controller;

import _3team.indev3teambackend.ChatRoom.dto.ChatRoomDto;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import _3team.indev3teambackend.ChatRoom.repository.ChatRoomRepository;
import _3team.indev3teambackend.ChatRoom.service.ChatRoomService;
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
class ChatRoomServiceTest {
    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private ChatRoomService chatRoomService;

    private ChatRoomEntity chatRoomEntity1;
    private ChatRoomEntity chatRoomEntity2;

    // 테스트에 사용될 메시지 내용을 상수로 정의
    private final String MESSAGE_1 = "첫 번째 메시지";
    private final String MESSAGE_2 = "두 번째 메시지";

    @BeforeEach
    void setUp() {
        chatRoomEntity1 = new ChatRoomEntity();
        chatRoomEntity1.setId(1L);
        chatRoomEntity1.setChatRoomId(1L);
        chatRoomEntity1.setContent(MESSAGE_1);
        chatRoomEntity1.setCreateAt(LocalDateTime.now());

        chatRoomEntity2 = new ChatRoomEntity();
        chatRoomEntity2.setId(2L);
        chatRoomEntity2.setChatRoomId(1L);
        chatRoomEntity2.setContent(MESSAGE_2);
        chatRoomEntity2.setCreateAt(LocalDateTime.now().plusSeconds(10));
    }

    @Test
    @DisplayName("메시지 생성 테스트")
    void createChatTest() {
        // given
        ChatRoomDto chatRoomDto = ChatRoomDto.fromEntity(chatRoomEntity1);
        when(chatRoomRepository.save(any(ChatRoomEntity.class))).thenReturn(chatRoomEntity1);

        // when
        ChatRoomDto createdDto = chatRoomService.createChat(chatRoomDto);

        // then
        assertNotNull(createdDto);
        assertEquals(1L, createdDto.getChatRoomId());
        assertEquals(MESSAGE_1, createdDto.getContent()); // 상수를 사용하여 검증
        verify(chatRoomRepository, times(1)).save(any(ChatRoomEntity.class));
    }

    @Test
    @DisplayName("특정 채팅방 메시지 목록 조회 테스트")
    void getChatsByChatRoomIdTest() {
        // given
        when(chatRoomRepository.findByChatRoomIdOrderByCreateAtAsc(1L))
                .thenReturn(Arrays.asList(chatRoomEntity1, chatRoomEntity2));

        // when
        List<ChatRoomDto> chatList = chatRoomService.getChatsByChatRoomId(1L);

        // then
        assertNotNull(chatList);
        assertEquals(2, chatList.size());
        assertEquals(MESSAGE_1, chatList.get(0).getContent()); // 상수를 사용하여 검증
        assertEquals(MESSAGE_2, chatList.get(1).getContent());
        verify(chatRoomRepository, times(1)).findByChatRoomIdOrderByCreateAtAsc(1L);
    }

    @Test
    @DisplayName("메시지 수정 테스트")
    void updateChatTest() {
        // given
        ChatRoomDto updatedDto = ChatRoomDto.fromEntity(chatRoomEntity1);
        updatedDto.setContent("수정된 메시지");
        when(chatRoomRepository.findById(1L)).thenReturn(Optional.of(chatRoomEntity1));
        when(chatRoomRepository.save(any(ChatRoomEntity.class))).thenReturn(chatRoomEntity1);

        // when
        ChatRoomDto resultDto = chatRoomService.updateChat(1L, updatedDto);

        // then
        assertNotNull(resultDto);
        assertEquals("수정된 메시지", resultDto.getContent());
        verify(chatRoomRepository, times(1)).findById(1L);
        verify(chatRoomRepository, times(1)).save(any(ChatRoomEntity.class));
    }

    @Test
    @DisplayName("메시지 삭제 테스트")
    void deleteChatTest() {
        // given
        when(chatRoomRepository.existsById(1L)).thenReturn(true);
        doNothing().when(chatRoomRepository).deleteById(1L);

        // when
        chatRoomService.deleteChat(1L);

        // then
        verify(chatRoomRepository, times(1)).existsById(1L);
        verify(chatRoomRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 메시지 삭제 시 예외 발생 테스트")
    void deleteChatNotFoundTest() {
        // given
        when(chatRoomRepository.existsById(99L)).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> chatRoomService.deleteChat(99L));
        verify(chatRoomRepository, times(1)).existsById(99L);
        verify(chatRoomRepository, never()).deleteById(anyLong());
    }
}
