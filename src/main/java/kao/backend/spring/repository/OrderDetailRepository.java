package kao.backend.spring.repository;

import kao.backend.spring.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity , Integer> {
}
