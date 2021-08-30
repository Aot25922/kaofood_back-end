package kao.backend.spring.model;

import javax.persistence.*;

@Entity
@Table(name = "menu", schema = "kaofood", catalog = "")
public class MenuEntity {
    @Id
    private int id;
    private String name;
    private float price;
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name = "cateId")
    private CategoryEntity category;

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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        MenuEntity that = (MenuEntity) o;
//
//        if (menuId != that.menuId) return false;
//        if (menuName != null ? !menuName.equals(that.menuName) : that.menuName != null) return false;
//        if (price != null ? !price.equals(that.price) : that.price != null) return false;
//        if (description != null ? !description.equals(that.description) : that.description != null) return false;
//        if (image != null ? !image.equals(that.image) : that.image != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = menuId;
//        result = 31 * result + (menuName != null ? menuName.hashCode() : 0);
//        result = 31 * result + (price != null ? price.hashCode() : 0);
//        result = 31 * result + (description != null ? description.hashCode() : 0);
//        result = 31 * result + (image != null ? image.hashCode() : 0);
//        return result;
//    }
}
