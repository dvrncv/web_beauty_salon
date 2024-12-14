package beauty_salon.DTO;

import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.EmployeeEntity;
import beauty_salon.entities.ServiceEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentServiceDTO {
    private LocalDate dateStart;
    private LocalTime timeStart;
    private Integer totalCost;
    private Long clientId;
    private Long serviceId;
    private Long employeeId;

    public AppointmentServiceDTO(LocalDate dateStart, LocalTime timeStart, Integer totalCost, Long clientId, Long serviceId, Long employeeId) {
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.totalCost = totalCost;
        this.clientId = clientId;
        this.serviceId = serviceId;
        this.employeeId = employeeId;
    }
    protected AppointmentServiceDTO(){}

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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
