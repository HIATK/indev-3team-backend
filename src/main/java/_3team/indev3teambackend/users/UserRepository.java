package _3team.indev3teambackend.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByProviderIdEmail(String providerIdEmail);
}
