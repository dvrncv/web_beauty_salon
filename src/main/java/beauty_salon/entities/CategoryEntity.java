package beauty_salon.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
    private String name;
    private String description;
    private List<ServiceEntity> serviceEntityList;

    public CategoryEntity(String name, String description, List<ServiceEntity> serviceEntityList) {
        this.name = name;
        this.description = description;
        this.serviceEntityList = serviceEntityList;
    }

    protected CategoryEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, targetEntity = ServiceEntity.class, mappedBy = "category")
    public List<ServiceEntity> getServiceEntityList() {
        return serviceEntityList;
    }

    public void setServiceEntityList(List<ServiceEntity> serviceEntityList) {
        this.serviceEntityList = serviceEntityList;
    }
}

