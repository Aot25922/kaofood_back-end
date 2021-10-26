package kao.backend.spring.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category", schema = "kaofood")
public class CategoryEntity {
    @Id
    private int id;
    private String name;
    private String image;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<MenuEntity> menuList;

    public int getId() {
        return id;
    }

    public void setId(int cateId) {
        this.id = cateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String cateName) {
        this.name = cateName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMenuList(List<MenuEntity> menuList) {
        this.menuList = menuList;
    }

}
