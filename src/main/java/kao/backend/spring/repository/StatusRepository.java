package kao.backend.spring.repository;

import kao.backend.spring.model.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity , Integer> {
}
