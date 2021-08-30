package kao.backend.spring.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order", schema = "kaofood", catalog = "")
public class OrderEntity {
    @Id
    private int id;
    private float totalPrice;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "statusId")
    private StatusEntity status;
    @OneToMany(mappedBy = "order")
    private List<OrderDetailEntity> orderDetail;

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

    public UserEntity getUser() {
        return user;
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

    public List<OrderDetailEntity> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailEntity> orderDetail) {
        this.orderDetail = orderDetail;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        OrderEntity that = (OrderEntity) o;
//
//        if (orderId != that.orderId) return false;
//        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = orderId;
//        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
//        return result;
//    }
}
