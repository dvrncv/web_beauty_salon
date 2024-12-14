package beauty_salon.entities;

import beauty_salon.enums.StaffRoles;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    private StaffRoles name;

    public Role(StaffRoles name) {
        this.name = name;
    }

    public Role() {

    }

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    public StaffRoles getName() {
        return name;
    }

    public void setName(StaffRoles name) {
        this.name = name;
    }
}
