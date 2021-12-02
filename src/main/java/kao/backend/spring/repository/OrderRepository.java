package kao.backend.spring.repository;

import kao.backend.spring.model.OrderEntity;
import kao.backend.spring.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity , Integer> {
    OrderEntity findById(int id);
    List<OrderEntity> findAllByUser_Id(int id);
    @Transactional
    @Modifying
    @Query("delete from OrderEntity o where o.user.id = ?1")
    void deleteByUser_Id(int id);

    OrderEntity findTopByOrderByIdDesc();
}
