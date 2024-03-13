package com.okohl.wagetracker.adapter.repositories;

import com.okohl.wagetracker.domain.PayrollHoursStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.persistence.EnumType;

import java.time.YearMonth;

@Entity
public class PayrollHoursEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private YearMonth month;
    private Float hours;

    @Enumerated(EnumType.STRING)
    private PayrollHoursStatus status;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    private PayrollHoursEntity() {
    }

    public PayrollHoursEntity(YearMonth month, EmployeeEntity employee, Float hours, PayrollHoursStatus status) {
        this.month = month;
        this.employee = employee;
        this.hours = hours;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public YearMonth getMonth() {
        return month;
    }

    public Float getHours() {
        return hours;
    }

    public PayrollHoursStatus getStatus() {
        return status;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public void setStatus(PayrollHoursStatus status) {
        this.status = status;
    }
}
