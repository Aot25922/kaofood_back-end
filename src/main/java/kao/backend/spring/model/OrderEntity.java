package kao.backend.spring.model;

import javax.persistence.*;

@Entity
@Table(name = "order", schema = "kaofood", catalog = "")
public class OrderEntity {
    @Id
    private int orderId;
    private float totalPrice;
    @ManyToOne
    @JoinColumn(name = "menuid")
    private MenuEntity menuid;
    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userid;

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

    public MenuEntity getMenuid() {
        return menuid;
    }

    public void setMenuid(MenuEntity menuid) {
        this.menuid = menuid;
    }

    public UserEntity getUserid() {
        return userid;
    }

    public void setUserid(UserEntity userid) {
        this.userid = userid;
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
