package _3team.indev3teambackend.users.dto;

import jakarta.validation.constraints.NotBlank;

// 신규 사용자 요청 DTO
/* Request: {
    "providerId": "12312321" 
    "providerIdEmail": "aaa@gmail.com"
    "name": "엄준식", // 또는 "미연동 계정"
    "profileImage": "/path/to/image.jpg" // 옵셔널
} */
public class UserCreateRequest {

    @NotBlank(message = "providerId is Required")
    private final String providerId;
    private final String providerIdEmail;
    private final String name;
    private final String profileImageUrl;

    public UserCreateRequest(String providerId, String providerIdEmail, String name, String profileImageUrl) {
        this.providerId = providerId;
        this.providerIdEmail = providerIdEmail;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderIdEmail() {
        return providerIdEmail;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
