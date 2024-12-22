package beauty_salon.DTO;

import beauty_salon.entities.LoyaltyCardEntity;

public class ClientDTO {
    private Long id;
    private String name;
    private String surname;
    private String numberPhone;
    private String email;
    private String password;

    public ClientDTO(Long id, String name, String surname, String numberPhone, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.numberPhone = numberPhone;
        this.email = email;
        this.password = password;
    }

    public ClientDTO() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
