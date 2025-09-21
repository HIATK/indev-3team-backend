package _3team.indev3teambackend.users.dto;

public class UserUpdateRequest {

    private String providerIdEmail;
    private String name;
    private String profileImageUrl;

    public UserUpdateRequest() {
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

    public void setProviderIdEmail(String providerIdEmail) { 
        this.providerIdEmail = providerIdEmail; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public void setProfileImageUrl(String profileImageUrl) { 
        this.profileImageUrl = profileImageUrl; 
    }
}
