package _3team.indev3teambackend.users.repository;

import _3team.indev3teambackend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);
    List<User> findByProviderId(String ProviderId);
}
