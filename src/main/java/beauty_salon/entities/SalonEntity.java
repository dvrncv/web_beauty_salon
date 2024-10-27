package beauty_salon.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "salon")
public class SalonEntity extends BaseEntity {
    private String name;
    private String address;
    private Integer numberPhone;
    private Integer rating;
    private List<ServiceEntity> serviceEntityList;
    private List<EmployeeEntity> employeeEntityList;

    public SalonEntity(String name, String address, Integer numberPhone, Integer rating, List<ServiceEntity> serviceEntityList, List<EmployeeEntity> employeeEntityList) {
        this.name = name;
        this.address = address;
        this.numberPhone = numberPhone;
        this.rating = rating;
        this.serviceEntityList = serviceEntityList;
        this.employeeEntityList = employeeEntityList;
    }

    protected SalonEntity() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "number_phone")
    public Integer getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(Integer numberPhone) {
        this.numberPhone = numberPhone;
    }

    @Column(name = "rating")
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @OneToMany(fetch = FetchType.LAZY, targetEntity = ServiceEntity.class, mappedBy = "salon")
    public List<ServiceEntity> getServiceEntityList() {
        return serviceEntityList;
    }

    public void setServiceEntityList(List<ServiceEntity> serviceEntityList) {
        this.serviceEntityList = serviceEntityList;
    }

    @OneToMany(fetch = FetchType.LAZY, targetEntity = EmployeeEntity.class, mappedBy = "salon")
    public List<EmployeeEntity> getEmployeeEntityList() {
        return employeeEntityList;
    }

    public void setEmployeeEntityList(List<EmployeeEntity> employeeEntityList) {
        this.employeeEntityList = employeeEntityList;
    }
}
