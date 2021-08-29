package kao.backend.spring.repository;

import kao.backend.spring.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAllByEmailAndPassword (String email,String password);
}
