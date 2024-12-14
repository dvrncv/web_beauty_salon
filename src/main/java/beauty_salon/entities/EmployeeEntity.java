package beauty_salon.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
public class EmployeeEntity extends BaseEntity {
    private String name;
    private String surname;
    private String numberPhone;
    private String email;
    private String password;
    private List<Role> roles;
    private List<AppointmentServiceEntity> appointmentServiceEntityList;
    private List<GrafikEntity> grafikEntityList;

    public EmployeeEntity() {
        this.roles = new ArrayList<>();
    }

    public EmployeeEntity(String name, String surname, String numberPhone, String email, String password) {
        this();
        this.name = name;
        this.surname = surname;
        this.numberPhone = numberPhone;
        this.email = email;
        this.password = password;
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

    @Column(name = "number_phone")
    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(fetch = FetchType.LAZY, targetEntity = AppointmentServiceEntity.class, mappedBy = "employee")
    public List<AppointmentServiceEntity> getAppointmentServiceEntityList() {
        return appointmentServiceEntityList;
    }

    public void setAppointmentServiceEntityList(List<AppointmentServiceEntity> appointmentServiceEntityList) {
        this.appointmentServiceEntityList = appointmentServiceEntityList;
    }

    @OneToMany(fetch = FetchType.LAZY, targetEntity = GrafikEntity.class, mappedBy = "employee")
    public List<GrafikEntity> getGrafikEntityList() {
        return grafikEntityList;
    }

    public void setGrafikEntityList(List<GrafikEntity> grafikEntityList) {
        this.grafikEntityList = grafikEntityList;
    }
}
