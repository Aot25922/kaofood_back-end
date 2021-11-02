package kao.backend.spring.repository;

import kao.backend.spring.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity , Integer> {
    List<OrderDetailEntity> findAllByOrders_User_Id(int id);
}
