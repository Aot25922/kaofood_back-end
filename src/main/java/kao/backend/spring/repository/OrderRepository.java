package kao.backend.spring.repository;

import kao.backend.spring.model.OrderEntity;
import kao.backend.spring.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity , Integer> {
    OrderEntity findByUser_Id(int id);
    OrderEntity findByUser(UserEntity user);
    OrderEntity findById(int id);

    List<OrderEntity> findAllByUser_Id(int id);
}
