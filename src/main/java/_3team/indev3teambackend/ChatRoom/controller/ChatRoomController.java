package _3team.indev3teambackend.ChatRoom.controller;

import _3team.indev3teambackend.ChatRoom.dto.ChatRoomDto;
import _3team.indev3teambackend.ChatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    // Post /api/chat -> 채팅 생성하기
    @PostMapping
    public ResponseEntity<ChatRoomDto> createChat(@RequestBody ChatRoomDto chatRoomDto){
        ChatRoomDto createdChat = chatRoomService.createChat(chatRoomDto);
        return ResponseEntity.ok(createdChat);
    }

    // GET /api/chat/{chatRoomId} : 특정 채팅방 메세지 목록 조회하기
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<ChatRoomDto>> getChatsByChatRoomId(@PathVariable Long chatRoomId){
        List<ChatRoomDto> chats = chatRoomService.getChatsByChatRoomId(chatRoomId);
        return ResponseEntity.ok(chats);
    }

    // PUT /api/chat/{id} : 메세지 수정 로직
    @PutMapping
    public ResponseEntity<ChatRoomDto> updateChat(@PathVariable Long id, @RequestBody ChatRoomDto chatRoomDto){
        ChatRoomDto updatedChat = chatRoomService.updateChat(id, chatRoomDto);
        return ResponseEntity.ok(updatedChat);
    }

    //DELETE /api/chat/{id} :  메세지 삭제 로직
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id){
        chatRoomService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }
}
