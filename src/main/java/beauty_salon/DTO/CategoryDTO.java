package beauty_salon.DTO;

import beauty_salon.entities.ServiceEntity;

import java.util.List;

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private List<ServiceEntity> serviceEntityList;

    public CategoryDTO(Long id, String name, String description, List<ServiceEntity> serviceEntityList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.serviceEntityList = serviceEntityList;
    }

    public CategoryDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ServiceEntity> getServiceEntityList() {
        return serviceEntityList;
    }

    public void setServiceEntityList(List<ServiceEntity> serviceEntityList) {
        this.serviceEntityList = serviceEntityList;
    }
}
