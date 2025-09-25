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

    //새로운 채팅방 생성 메서드
    //POST -> /api/chat_rooms/{userid}
    @PostMapping("/{userId}")
    public ChatRoomDto createChatRoom(@PathVariable Long userId)    {
        return chatRoomService.createChatRoom(userId);
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
