package kao.backend.spring.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu", schema = "kaofood")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private float price;
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name = "cateId")
    private CategoryEntity category;
    @OneToMany(mappedBy = "menu")
    private List<OrderDetailEntity> orderDetailList;

    public int getId() {
        return id;
    }

    public void setId(int menuId) {
        this.id = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String menuName) {
        this.name = menuName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity categoryId) {
        this.category = categoryId;
    }

    public void setOrderDetailList(List<OrderDetailEntity> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

}
