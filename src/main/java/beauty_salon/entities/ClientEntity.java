package beauty_salon.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class ClientEntity extends BaseEntity {
    private String name;
    private String surname;
    private String numberPhone;
    private String email;
    private String password;
    private LoyaltyCardEntity loyaltyCard;
    private List<AppointmentServiceEntity> appointmentServiceEntityList;


    public ClientEntity(String name, String surname, String numberPhone, String email, String password, LoyaltyCardEntity loyaltyCard, List<AppointmentServiceEntity> appointmentServiceEntityList) {
        this.name = name;
        this.surname = surname;
        this.numberPhone = numberPhone;
        this.email = email;
        this.password = password;
        this.loyaltyCard = loyaltyCard;
        this.appointmentServiceEntityList = appointmentServiceEntityList;
    }
    protected ClientEntity(){

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

    @OneToMany(fetch = FetchType.LAZY, targetEntity = AppointmentServiceEntity.class, mappedBy = "client")
    public List<AppointmentServiceEntity> getAppointmentServiceEntityList() {
        return appointmentServiceEntityList;
    }

    public void setAppointmentServiceEntityList(List<AppointmentServiceEntity> appointmentServiceEntityList) {
        this.appointmentServiceEntityList = appointmentServiceEntityList;
    }

    @OneToOne(fetch = FetchType.LAZY, targetEntity = LoyaltyCardEntity.class, mappedBy = "client")
    public LoyaltyCardEntity getLoyaltyCard() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(LoyaltyCardEntity loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }
}
