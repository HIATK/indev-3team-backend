package _3team.indev3teambackend.ChatMessage.repository;

import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ChatMessagesRepository extends JpaRepository<ChatMessagesEntity,Long> {
    // chatRoomId 기준으로 메세지 목록을 찾는 메서드임
    List<ChatMessagesEntity> findByChatMessagesIdOrderByCreateAtAsc(Long chatRoomId);
}
