package _3team.indev3teambackend.users.dto;

import jakarta.validation.constraints.NotBlank;

public class UserCreateRequest {

    @NotBlank(message = "providerId는 필수입니다.")
    private String providerId;

    private String providerIdEmail;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    
    private String profileImageUrl;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderIdEmail() {
        return providerIdEmail;
    }

    public void setProviderIdEmail(String providerIdEmail) {
        this.providerIdEmail = providerIdEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
