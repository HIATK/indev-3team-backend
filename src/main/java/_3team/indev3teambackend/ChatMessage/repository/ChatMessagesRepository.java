package _3team.indev3teambackend.ChatMessage.repository;

import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ChatMessagesRepository extends JpaRepository<ChatMessagesEntity,Long> {
    // chatRoomId 기준으로 메세지 목록을 찾는 메서드임
    //List<ChatMessagesEntity> findByChatMessagesIdOrderByCreateAtAsc(Long chatRoomId);

    // 외래 키 매핑 필드(ChatRoomEntity chatRoom)를 통해 접근하는 경우:
    List<ChatMessagesEntity> findByChatRoomIdOrderByCreateAtAsc(Long chatRoomId);

}
