package kao.backend.spring.repository;

import kao.backend.spring.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmailAndPassword(String email,String password);
    UserEntity findByEmail(String email);
    UserEntity findByPhone(String phone);
    UserEntity findById(int id);
}
