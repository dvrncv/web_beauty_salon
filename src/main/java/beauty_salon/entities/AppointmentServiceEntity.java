package beauty_salon.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointment_service")
public class AppointmentServiceEntity extends BaseEntity {
    private LocalDate dateStart;
    private LocalTime timeStart;
    private Integer totalCost;
    private ClientEntity client;
    private ServiceEntity service;
    private EmployeeEntity employee;
    private boolean isPointsAdded;

    public AppointmentServiceEntity(LocalDate dateStart, LocalTime timeStart, Integer totalCost, ClientEntity client, ServiceEntity service, EmployeeEntity employee, boolean isPointsAdded) {
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.totalCost = totalCost;
        this.client = client;
        this.service = service;
        this.employee = employee;
        this.isPointsAdded = isPointsAdded;
    }

    protected AppointmentServiceEntity() {
    }

    @Column(name = "date_start")
    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    @Column(name = "time_start")
    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    @Column(name = "total_cost")
    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    @Column(name = "points")
    public boolean isPointsAdded() {
        return isPointsAdded;
    }

    public void setPointsAdded(boolean pointsAdded) {
        isPointsAdded = pointsAdded;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
