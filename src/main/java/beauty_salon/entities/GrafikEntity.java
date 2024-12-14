package beauty_salon.entities;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "grafik")
public class GrafikEntity extends BaseEntity{
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private EmployeeEntity employee;

    public GrafikEntity(String dayOfWeek, LocalTime startTime, LocalTime endTime, EmployeeEntity employee) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
    }
    protected GrafikEntity(){
    }
    @Column(name = "date_of_week")
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
