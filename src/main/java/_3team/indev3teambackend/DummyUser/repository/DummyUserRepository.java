package _3team.indev3teambackend.DummyUser.repository;

import _3team.indev3teambackend.DummyUser.EntityDummy.DummyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DummyUserRepository extends JpaRepository<DummyEntity,Long> {
    List<DummyEntity> findByName(String name);
    List<DummyEntity> findByProviderId(String ProviderId);
}
