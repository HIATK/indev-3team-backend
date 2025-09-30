package _3team.indev3teambackend.users.dto;

import lombok.Getter;

// 신규 사용자 응답 DTO
/* Response: {
    "success": true,
    "id": 1
} */
@Getter
public class UserCreateResponse {

    private final boolean success;
    private final Long id;

    public UserCreateResponse(boolean success, Long id) {
        this.success = success;
        this.id = id;
    }
    
}
