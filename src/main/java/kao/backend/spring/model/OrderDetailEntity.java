package kao.backend.spring.model;

import javax.persistence.*;

@Entity
@Table(name = "orderDetail", schema = "kaofood", catalog = "")
public class OrderDetailEntity {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "menuId")
    private MenuEntity menu;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailEntity that = (OrderDetailEntity) o;

        if (id != that.id) return false;
        if (count != that.count) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + count;
        return result;
    }
}
