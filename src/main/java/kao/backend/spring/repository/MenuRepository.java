package kao.backend.spring.repository;

import kao.backend.spring.model.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

    List<MenuEntity> findMenuEntityByMenuName(String menuName);
}
