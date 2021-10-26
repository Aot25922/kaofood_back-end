package kao.backend.spring.model;

import javax.persistence.*;

@Entity
@Table(name = "orderDetail", schema = "kaofood")
public class OrderDetailEntity {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "ordersId", nullable = false)
    private OrderEntity orders;
    @ManyToOne
    @JoinColumn(name = "menuId", nullable = false)
    private MenuEntity menu;
    private int count;

    public OrderDetailEntity(OrderEntity orders, MenuEntity menu, int count) {
        this.orders = orders;
        this.menu = menu;
        this.count = count;
    }

    public OrderDetailEntity() {

    }

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

    public void setOrders(OrderEntity order) {
        this.orders = order;
    }

    public OrderEntity getOrders() { return orders; }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }


}
