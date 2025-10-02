package _3team.indev3teambackend.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

// 신규 사용자 요청 DTO
/* Request: {
    "providerId": "12312321" 
    "providerIdEmail": "aaa@gmail.com"
    "name": "엄준식", // 또는 "미연동 계정"
    "profileImage": "/path/to/image.jpg" // 옵셔널
} */
@Getter
public class UserCreateRequest {

    @NotBlank(message = "providerId is Required")
    private final String providerId;
    private final String providerIdEmail;
    private final String name;
    private final String profileImageUrl;

    // @JsonProperty를 통해 profileImageUrl 필드를 JSON 필드(profileImage)와 매핑
    public UserCreateRequest(String providerId, String providerIdEmail, String name, @JsonProperty("profileImage") String profileImageUrl) { 
        this.providerId = providerId;
        this.providerIdEmail = providerIdEmail;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

}
