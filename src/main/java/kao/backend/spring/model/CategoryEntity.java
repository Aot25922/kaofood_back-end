package kao.backend.spring.model;

import javax.persistence.*;

@Entity
@Table(name = "category", schema = "kaofood", catalog = "")
public class CategoryEntity {
    @Id
    private int cateId;
    private String cateName;
    private String image;
    private String description;

    @Id
    @Column(name = "cateId")
    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    @Basic
    @Column(name = "cateName")
    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (cateId != that.cateId) return false;
        if (cateName != null ? !cateName.equals(that.cateName) : that.cateName != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cateId;
        result = 31 * result + (cateName != null ? cateName.hashCode() : 0);
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
