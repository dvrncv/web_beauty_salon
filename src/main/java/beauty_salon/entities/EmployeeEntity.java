package beauty_salon.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "employee")
public class EmployeeEntity extends BaseEntity {
    private String name;
    private String surname;
    private Integer workExperience;
    private Integer numberPhone;
    private SalonEntity salon;
    private List<AppointmentServiceEntity> appointmentServiceEntityList;

    public EmployeeEntity(String name, String surname, Integer workExperience, Integer numberPhone, SalonEntity salon, List<AppointmentServiceEntity> appointmentServiceEntityList) {
        this.name = name;
        this.surname = surname;
        this.workExperience = workExperience;
        this.numberPhone = numberPhone;
        this.salon = salon;
        this.appointmentServiceEntityList = appointmentServiceEntityList;
    }

    protected EmployeeEntity() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "work_experience")
    public Integer getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience;
    }

    @Column(name = "number_phone")
    public Integer getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(Integer numberPhone) {
        this.numberPhone = numberPhone;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salon_id")
    public SalonEntity getSalon() {
        return salon;
    }

    public void setSalon(SalonEntity salon) {
        this.salon = salon;
    }
    @OneToMany(fetch = FetchType.LAZY, targetEntity = AppointmentServiceEntity.class, mappedBy = "employee")
    public List<AppointmentServiceEntity> getAppointmentServiceEntityList() {
        return appointmentServiceEntityList;
    }

    public void setAppointmentServiceEntityList(List<AppointmentServiceEntity> appointmentServiceEntityList) {
        this.appointmentServiceEntityList = appointmentServiceEntityList;
    }
}
