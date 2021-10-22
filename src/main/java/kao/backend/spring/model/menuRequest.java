package kao.backend.spring.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class menuRequest {
    private int menuId;
    private int count;

    @JsonCreator
    public menuRequest(int menuId, int count) {
        this.menuId = menuId;
        this.count = count;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
