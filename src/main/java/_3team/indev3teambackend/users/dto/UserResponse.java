package _3team.indev3teambackend.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import _3team.indev3teambackend.users.User;

// 사용자 정보 반환 DTO
/* Response: {
    "id": 1,
    "name": "홍대경",
    "imagePath": "/path/to/image.jpg"
} */
@Getter
public class UserResponse {

    private final Long id;
    private final String name;
    @JsonProperty("imagePath") private final String profileImageUrl;

    public UserResponse(Long id, String name, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public static UserResponse from(User u) {
        return new UserResponse(
            u.getId(), 
            u.getName(), 
            u.getProfileImageUrl()
        );
    }

}
