package kao.backend.spring.repository;

import kao.backend.spring.model.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<StatusEntity , Integer> {
    StatusEntity findById(int id);
    List<StatusEntity> findAllByOrderByIdAsc();
}
