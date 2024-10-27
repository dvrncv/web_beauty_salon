package beauty_salon.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "client")
public class ClientEntity extends BaseEntity {

    private String name;
    private String surname;
    private Integer numberPhone;
    private String email;
    private LoyaltyCardEntity loyaltyCard;
    private List<AppointmentServiceEntity> appointmentServiceEntityList;

    public ClientEntity(String name, String surname, Integer numberPhone, String email, LoyaltyCardEntity loyaltyCard, List<AppointmentServiceEntity> appointmentServiceEntityList) {
        this.name = name;
        this.surname = surname;
        this.numberPhone = numberPhone;
        this.email = email;
        this.loyaltyCard = loyaltyCard;
        this.appointmentServiceEntityList = appointmentServiceEntityList;
    }

    protected ClientEntity() {
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
    public Integer getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(Integer numberPhone) {
        this.numberPhone = numberPhone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
