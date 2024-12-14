package beauty_salon.DTO;


public class EmployeeDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Integer clientCount;

    public EmployeeDTO(Long id, String name, String surname, String email, Integer clientCount) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.clientCount = clientCount;
    }

    public EmployeeDTO(){

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getClientCount() {
        return clientCount;
    }

    public void setClientCount(Integer clientCount) {
        this.clientCount = clientCount;
    }
}
