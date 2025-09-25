package _3team.indev3teambackend.ChatMessage.controller;

import _3team.indev3teambackend.ChatMessage.dto.ChatMessagesDto;
import _3team.indev3teambackend.ChatMessage.service.ChatMessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatMessagesController {
    private final ChatMessagesService chatMessagesService;

    // Post /api/chat -> 채팅 생성하기
    @PostMapping
    public ResponseEntity<ChatMessagesDto> createChat(@RequestBody ChatMessagesDto chatMessagesDto){
        ChatMessagesDto createdChat = chatMessagesService.createChat(chatMessagesDto);
        return ResponseEntity.ok(createdChat);
    }

    // GET /api/chat/{chatMessagesId} : 특정 채팅방 메세지 목록 조회하기
    @GetMapping("/{chatMessagesId}")
    public ResponseEntity<List<ChatMessagesDto>> getChatsBychatMessagesId(@PathVariable Long chatMessagesId){
        List<ChatMessagesDto> chats = chatMessagesService.getChatsByChatMessagesId(chatMessagesId);
        return ResponseEntity.ok(chats);
    }

    // PUT /api/chat/{id} : 메세지 수정 로직
    @PutMapping
    public ResponseEntity<ChatMessagesDto> updateChat(@PathVariable Long id, @RequestBody ChatMessagesDto chatMessagesDto){
        ChatMessagesDto updatedChat = chatMessagesService.updateChat(id, chatMessagesDto);
        return ResponseEntity.ok(updatedChat);
    }

    //DELETE /api/chat/{id} :  메세지 삭제 로직
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id){
        chatMessagesService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }
}
