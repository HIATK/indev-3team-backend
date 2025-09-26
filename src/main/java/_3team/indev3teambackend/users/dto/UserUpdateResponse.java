package _3team.indev3teambackend.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import _3team.indev3teambackend.users.User;

// 사용자 정보 수정 DTO
/* Response: {
    "id": 1,
    "name": "엄준식",
    "imagePath": "/path/to/image.jpg"
} */
public class UserUpdateResponse {

    private final Long id;
    private final String name;
    private final String profileImageUrl;

    public UserUpdateResponse(Long id, String name, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("imagePath")
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static UserUpdateResponse from(User u) {
        return new UserUpdateResponse(
            u.getId(), 
            u.getName(), 
            u.getProfileImageUrl()
        );
    }
}