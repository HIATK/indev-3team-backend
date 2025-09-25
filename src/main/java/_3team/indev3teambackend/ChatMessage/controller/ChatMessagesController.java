package _3team.indev3teambackend.ChatMessage.controller;

import _3team.indev3teambackend.ChatMessage.dto.ChatMessagesDto;
import _3team.indev3teambackend.ChatMessage.service.ChatMessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat") // 기본 경로: /api/chat
@RequiredArgsConstructor
public class ChatMessagesController {

    private final ChatMessagesService chatMessagesService;

    // POST /api/chat -> 채팅 메시지 생성 (FK는 Body에 포함)
    @PostMapping
    public ResponseEntity<ChatMessagesDto> createChat(@RequestBody ChatMessagesDto chatMessagesDto){
        ChatMessagesDto createdChat = chatMessagesService.createChat(chatMessagesDto);
        return ResponseEntity.ok(createdChat);
    }

    // GET /api/chat/room/{chatRoomId} : 특정 채팅방 메세지 목록 조회하기
    // ✅ 경로를 명확히 하고, 변수 이름을 chatRoomId로 변경했습니다.
    @GetMapping("/room/{chatRoomId}")
    public ResponseEntity<List<ChatMessagesDto>> getChatsByChatRoomId(@PathVariable Long chatRoomId){
        // 서비스 메서드 이름도 getChatsByChatRoomId로 변경했다고 가정합니다.
        List<ChatMessagesDto> chats = chatMessagesService.getChatsByChatRoomId(chatRoomId);
        return ResponseEntity.ok(chats);
    }

    // PUT /api/chat/{id} : 메세지 수정 로직
    // ✅ @PutMapping 어노테이션에 /{id} 경로 변수를 추가했습니다.
    @PutMapping("/{id}")
    public ResponseEntity<ChatMessagesDto> updateChat(@PathVariable Long id, @RequestBody ChatMessagesDto chatMessagesDto){
        ChatMessagesDto updatedChat = chatMessagesService.updateChat(id, chatMessagesDto);
        return ResponseEntity.ok(updatedChat);
    }

    // DELETE /api/chat/{id} :  메세지 삭제 로직
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id){
        chatMessagesService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }
}