package _3team.indev3teambackend.users.dto;

import _3team.indev3teambackend.users.User;

public class UserResponse {

    private Long id;
    private String providerId;
    private String providerIdEmail;
    private String name;
    private String profileImageUrl;

    public UserResponse(Long id, String providerId, String providerIdEmail, String name, String profileImageUrl) {
        this.id = id;
        this.providerId = providerId;
        this.providerIdEmail = providerIdEmail;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public Long getId() {
        return id;
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

    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getProviderId(), u.getProviderIdEmail(), u.getName(), u.getProfileImageUrl());
    }

    public static class Builder {

        private Long id;
        private String providerId;
        private String providerIdEmail;
        private String name;
        private String profileImageUrl;
        
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder providerId(String providerId) {
            this.providerId = providerId;
            return this;
        }

        public Builder providerIdEmail(String providerIdEmail) {
            this.providerIdEmail = providerIdEmail;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(id, providerId, providerIdEmail, name, profileImageUrl);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
