package beauty_salon.DTO;


import java.time.LocalDate;

public class LoyaltyCardDTO {
    private Long numberCard;
    private Integer balancePoint;
    private LocalDate dateIssue;
    private String status;
    private String clientName;
    private String clientSurname;
    private Long clientId;

    public LoyaltyCardDTO(Long numberCard, Integer balancePoint, LocalDate dateIssue, String status, String clientName, String clientSurname, Long clientId) {
        this.numberCard = numberCard;
        this.balancePoint = balancePoint;
        this.dateIssue = dateIssue;
        this.status = status;
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.clientId = clientId;
    }

    public LoyaltyCardDTO() {

    }

    public Integer getBalancePoint() {
        return balancePoint;
    }

    public void setBalancePoint(Integer balancePoint) {
        this.balancePoint = balancePoint;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDate getDateIssue() {
        return dateIssue;
    }

    public void setDateIssue(LocalDate dateIssue) {
        this.dateIssue = dateIssue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public Long getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(Long numberCard) {
        this.numberCard = numberCard;
    }
}
