package kao.backend.spring.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order", schema = "kaofood", catalog = "")
public class OrderEntity {
    @Id
    private int orderId;
    private float totalPrice;
    @ManyToOne
    @JoinColumn(name = "statusId")
    private StatusEntity statusId;
    @ManyToMany
    @JoinTable(
            name = "orderDetail",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "menuId"))
    private List<MenuEntity> menuList;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
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
