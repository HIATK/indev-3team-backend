package _3team.indev3teambackend.users.dto;

// 사용자 정보 수정 DTO
/* Request: {
    "name": "엄준식",
    "imagePath": "/path/to/image.jpg"
} */
public class UserUpdateRequest {

    private final String name;
    private final String profileImageUrl;

    public UserUpdateRequest(String name, String profileImageUrl) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() { 
        return name; 
    }

    public String getProfileImageUrl() { 
        return profileImageUrl; 
    }
}
