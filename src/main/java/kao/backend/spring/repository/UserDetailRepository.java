package kao.backend.spring.repository;

import kao.backend.spring.model.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Integer> {
}
