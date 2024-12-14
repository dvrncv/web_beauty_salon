package beauty_salon.entities;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "service")
public class ServiceEntity extends BaseEntity {
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    private boolean isDeleted;
    private CategoryEntity category;
    private List<AppointmentServiceEntity> appointmentServiceEntityList;

    public ServiceEntity(String name, String description, Integer price, Integer duration, boolean isDeleted,  CategoryEntity category, List<AppointmentServiceEntity> appointmentServiceEntityList) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.isDeleted = isDeleted;
        this.category = category;
        this.appointmentServiceEntityList = appointmentServiceEntityList;
    }

    protected ServiceEntity() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Column(name = "duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @OneToMany(fetch = FetchType.LAZY, targetEntity = AppointmentServiceEntity.class, mappedBy = "service")
    public List<AppointmentServiceEntity> getAppointmentServiceEntityList() {
        return appointmentServiceEntityList;
    }

    public void setAppointmentServiceEntityList(List<AppointmentServiceEntity> appointmentServiceEntityList) {
        this.appointmentServiceEntityList = appointmentServiceEntityList;
    }

    @Column(name = "is_deleted")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
