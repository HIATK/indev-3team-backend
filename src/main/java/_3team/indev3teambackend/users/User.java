package _3team.indev3teambackend.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "providerId", nullable = false, unique = true)
    private String providerId;

    @Column(name = "providerId_email", unique = true)
    private String providerIdEmail;

    private String name;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    protected User() {
    }

    public User(String providerId, String providerIdEmail, String name, String profileImageUrl) {
        this.providerId = providerId;
        this.providerIdEmail = providerIdEmail;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public void changeProviderIdEmail(String providerIdEmail) { 
        this.providerIdEmail = providerIdEmail; 

    }

    public void changeName(String name) { 
        this.name = name; 
    }

    public void changeProfileImageUrl(String profileImageUrl) { 
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

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }

    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }

}
