package kao.backend.spring.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category", schema = "kaofood", catalog = "")
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

    public List<MenuEntity> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuEntity> menuList) {
        this.menuList = menuList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        TypeEntity that = (TypeEntity) o;
//
//        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
//        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
//        if (image != null ? !image.equals(that.image) : that.image != null) return false;
//        if (description != null ? !description.equals(that.description) : that.description != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = typeId != null ? typeId.hashCode() : 0;
//        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
//        result = 31 * result + (image != null ? image.hashCode() : 0);
//        result = 31 * result + (description != null ? description.hashCode() : 0);
//        return result;
//    }
}
