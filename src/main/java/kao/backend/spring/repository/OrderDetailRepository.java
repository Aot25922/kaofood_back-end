package kao.backend.spring.repository;

import kao.backend.spring.model.OrderDetailEntity;
import kao.backend.spring.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity , Integer> {
    @Transactional
    @Modifying
    @Query("delete from OrderDetailEntity o where o.orders.id = ?1")
    void deleteByOrders_Id(int id);

    @Transactional
    @Modifying
    @Query("delete from OrderDetailEntity od where od.orders.id IN (select o.id from OrderEntity o where o.user.id = ?1)")
    void deleteAllByUser_Id(int id);
}
