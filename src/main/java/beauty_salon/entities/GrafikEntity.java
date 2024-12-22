package beauty_salon.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "grafik")
public class GrafikEntity extends BaseEntity{
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
    private EmployeeEntity employee;

    public GrafikEntity( LocalDate day, LocalTime startTime, LocalTime endTime, EmployeeEntity employee) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
    }
    protected GrafikEntity(){
    }

    @Column(name = "day")
    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    @Column(name = "time_start")
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    @Column(name = "time_end")
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
