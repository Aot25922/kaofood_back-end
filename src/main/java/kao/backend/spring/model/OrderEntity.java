package kao.backend.spring.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders", schema = "kaofood")
public class OrderEntity {
    @Id
    private int id;
    private float totalPrice;
    @ManyToOne
    @JoinColumn(name = "userId" ,nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "statusId" ,nullable = false)
    private StatusEntity status;
    @OneToMany(mappedBy = "orders")
    private List<OrderDetailEntity> orderDetail;

    public OrderEntity() {
    }

    public OrderEntity(float totalPrice, UserEntity user, StatusEntity status) {
        this.totalPrice = totalPrice;
        this.user = user;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int orderId) {
        this.id = orderId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public void setOrderDetail(List<OrderDetailEntity> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public List<OrderDetailEntity> getOrderDetail() {
        return orderDetail;
    }
}
