package kao.backend.spring.repository;

import kao.backend.spring.model.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    boolean existsByNameIsLikeIgnoreCase(@NonNull String name);
    MenuEntity findById(int id);
    MenuEntity findByName(String name);

}
