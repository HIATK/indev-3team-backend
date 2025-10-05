package _3team.indev3teambackend.ChatRoom.controller;

import _3team.indev3teambackend.ChatRoom.dto.ChatRoomDto;
import _3team.indev3teambackend.ChatRoom.service.ChatRoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat_rooms") //URl set
public class ChatRoomController {



    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    // 💡 새로운 채팅방 생성 메서드: Llama와의 채팅방을 생성 (POST /api/chat_rooms/llama/{userId})
    @PostMapping("/llama/{userId}")
    public ChatRoomDto createLlamaChatRoom(@PathVariable Long userId)    {
        // 내부적으로 chatRoomService.createChatRoom(userId)는
        // Llama(ID=1)와 사용자(userId) 간의 채팅방을 생성하도록 구현되어 있습니다.
        return chatRoomService.createLlamaChatRoom(userId);
    }

    // 특정 사용자의 채팅방목록 조회
    //GET -> /api/user/{userId}
    @GetMapping("/user/{userId}")
    public List<ChatRoomDto> getChatRoomsByUser(@PathVariable Long userId){
        return chatRoomService.getChatRoomsByUserId(userId);
    }

    //특정 채팅방 상세 정보 조회
    // GET -> /api/chat_rooms/{chatRoomId}
    @GetMapping("/{chatRoomId}")
    public ChatRoomDto getChatRoom(@PathVariable Long chatRoomId){
        return chatRoomService.getChatRoomById(chatRoomId);
    }

}
