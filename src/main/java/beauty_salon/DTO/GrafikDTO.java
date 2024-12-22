package beauty_salon.DTO;

import beauty_salon.entities.EmployeeEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class GrafikDTO {
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long employeeId;

    public GrafikDTO(LocalDate day, LocalTime startTime, LocalTime endTime, Long employeeId) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employeeId = employeeId;
    }

    public GrafikDTO() {
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
