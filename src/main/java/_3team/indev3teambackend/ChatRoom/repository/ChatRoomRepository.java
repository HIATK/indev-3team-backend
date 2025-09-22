package _3team.indev3teambackend.ChatRoom.repository;

import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Long> {
    // chatRoomId 기준으로 메세지 목록을 찾는 메서드임
    List<ChatRoomEntity> findByChatRoomIdOrderByCreateAtAsc(Long chatRoomId);
}
