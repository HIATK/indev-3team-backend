package _3team.indev3teambackend.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

// 사용자 정보 수정 DTO
/* Request: {
    "name": "엄준식",
    "imagePath": "/path/to/image.jpg"
} */
@Getter
public class UserUpdateRequest {

    private final String name;
    private final String profileImageUrl;

    public UserUpdateRequest(String name, @JsonProperty("imagePath") String profileImageUrl) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
    
}
