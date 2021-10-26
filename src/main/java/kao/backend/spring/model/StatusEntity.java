package kao.backend.spring.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "status", schema = "kaofood", catalog = "")
public class StatusEntity {
    @Id
    private int id;
    private String name;
    @OneToMany(mappedBy = "status")
    private List<OrderEntity> orderList;

    public int getId() {
        return id;
    }

    public void setId(int statusId) {
        this.id = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String statusName) {
        this.name = statusName;
    }
}
