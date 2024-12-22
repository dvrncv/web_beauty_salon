package beauty_salon.DTO;

import jakarta.validation.constraints.*;

public class UserRegistrationDTO {
    private String name;

    private String surname;

    private String email;

    private String number_phone;

    private String password;

    private String confirmPassword;

    public UserRegistrationDTO() {
    }

    @NotEmpty(message = "User name cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    @NotEmpty(message = "Full name cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String fullname) {
        this.surname = fullname;
    }

    @NotEmpty(message = "Email cannot be null or empty!")
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Number cannot be null or empty!")
    @Size(min = 11, max = 11)
    public String getNumber_phone() {
        return number_phone;
    }

    public void setNumber_phone(String number_phone) {
        this.number_phone = number_phone;
    }

    @NotEmpty(message = "Password cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Confirm Password cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
