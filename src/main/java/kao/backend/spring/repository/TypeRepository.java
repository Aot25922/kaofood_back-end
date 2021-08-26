package kao.backend.spring.repository;

import kao.backend.spring.model.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository <TypeEntity,String> {
}
