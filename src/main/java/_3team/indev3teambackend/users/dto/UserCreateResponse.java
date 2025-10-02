package _3team.indev3teambackend.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 신규 사용자 응답 DTO
/* Response: {
    "success": true,
    "id": 1
} */
@Getter
@AllArgsConstructor // AllArgsConstructor는 모든 필드를 매개변수로 받는 생성자이며, 기본 접근제어자가 public임
public class UserCreateResponse {

    private final boolean success;
    private final Long id;
    
}
