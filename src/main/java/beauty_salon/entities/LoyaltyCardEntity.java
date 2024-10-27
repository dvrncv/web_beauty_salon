package beauty_salon.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "loyalty_card")
public class LoyaltyCardEntity {
    private Long numberCard;
    private LocalDate dateIssue;
    private String status;
    private Integer balancePoint;
    private ClientEntity client;

    public LoyaltyCardEntity(Long numberCard, LocalDate dateIssue, String status, Integer balancePoint, ClientEntity client) {
        this.numberCard = numberCard;
        this.dateIssue = dateIssue;
        this.status = status;
        this.balancePoint = balancePoint;
        this.client = client;
    }

    protected LoyaltyCardEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number_card")
    public Long getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(Long numberCard) {
        this.numberCard = numberCard;
    }

    @Column(name = "date_issue")
    public LocalDate getDateIssue() {
        return dateIssue;
    }

    public void setDateIssue(LocalDate dateIssue) {
        this.dateIssue = dateIssue;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "balance_point")
    public Integer getBalancePoint() {
        return balancePoint;
    }

    public void setBalancePoint(Integer balancePoint) {
        this.balancePoint = balancePoint;
    }

    @OneToOne(fetch = FetchType.LAZY,targetEntity = ClientEntity.class)
    @JoinColumn(name = "client_id", unique = true)
    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
