package kao.backend.spring.repository;

import kao.backend.spring.model.OrderEntity;
import kao.backend.spring.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity , Integer> {
    OrderEntity findByUser_Id(int id);
    OrderEntity findByUser(UserEntity user);
}
