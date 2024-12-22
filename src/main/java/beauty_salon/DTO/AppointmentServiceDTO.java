package beauty_salon.DTO;

import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.EmployeeEntity;
import beauty_salon.entities.ServiceEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentServiceDTO {
    private Long id;
    private LocalDate dateStart;
    private LocalTime timeStart;
    private Integer totalCost;
    private Long clientId;
    private String clientName;
    private String clientSurname;
    private String serviceName;
    private String employeeName;
    private String employeeSurname;
    private String time;
    private boolean isPointsAdded;
    private boolean hasLoyaltyCard;

    public AppointmentServiceDTO(LocalDate dateStart, LocalTime timeStart, Integer totalCost, Long clientId, Long serviceId, Long id, Long clientId1, String clientName, String clientSurname, String serviceName, String employeeName, String employeeSurname, String time, boolean isPointsAdded, boolean hasLoyaltyCard) {
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.totalCost = totalCost;
        this.id = id;
        this.clientId = clientId1;
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.serviceName = serviceName;
        this.employeeName = employeeName;
        this.employeeSurname = employeeSurname;
        this.time = time;
        this.isPointsAdded = isPointsAdded;
        this.hasLoyaltyCard = hasLoyaltyCard;
    }

    public AppointmentServiceDTO() {
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSurname() {
        return employeeSurname;
    }

    public void setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public boolean isPointsAdded() {
        return isPointsAdded;
    }

    public void setPointsAdded(boolean pointsAdded) {
        isPointsAdded = pointsAdded;
    }

    public boolean isHasLoyaltyCard() {
        return hasLoyaltyCard;
    }

    public void setHasLoyaltyCard(boolean hasLoyaltyCard) {
        this.hasLoyaltyCard = hasLoyaltyCard;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
