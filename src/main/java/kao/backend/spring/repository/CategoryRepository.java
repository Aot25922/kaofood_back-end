package kao.backend.spring.repository;

import kao.backend.spring.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <CategoryEntity, Integer> {
}
