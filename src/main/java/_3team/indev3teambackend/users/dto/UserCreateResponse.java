package _3team.indev3teambackend.users.dto;

// 신규 사용자 응답 DTO
/* Response: {
    "success": true,
    "id": 1
} */
public class UserCreateResponse {

    private final boolean success;
    private final Long id;

    public UserCreateResponse(boolean success, Long id) {
        this.success = success;
        this.id = id;
    }

    public boolean getSuccess() { return success; }

    public Long getId() { return id; }
}
